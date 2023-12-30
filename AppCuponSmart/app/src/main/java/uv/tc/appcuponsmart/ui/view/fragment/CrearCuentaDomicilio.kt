package uv.tc.appcuponsmart.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.controller.fragment.CrearCuentaDomicilioEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaDomicilioBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.model.CatalogoViewModel
import uv.tc.appcuponsmart.ui.viewmodel.model.CiudadViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CrearCuentaDomicilio: Fragment(){
    private var _binding: FragmentCrearCuentaDomicilioBinding? = null
    private val binding get() = _binding!!

    val catalogo: CatalogoViewModel by viewModels()
    val ciudad: CiudadViewModel by viewModels()

    private lateinit var event: CrearCuentaDomicilioEvent
    var cliente: String? = null
    var fotoByteArray: ByteArray? = byteArrayOf()

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        arguments?.let{
            cliente = it.getString(Constantes.Utileria.CLIENTE)
            fotoByteArray = it.getByteArray(Constantes.Utileria.FOTO)
        }

        event = CrearCuentaDomicilioEvent(this, binding, savedInstanceState)

        binding.btnContinuar.setOnClickListener(event)
        binding.itemsEstado.onItemSelectedListener = event
        binding.itemsMunicipio.onItemSelectedListener = event
        binding.itemsCiudad.onItemSelectedListener = event

        catalogo.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        ciudad.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        catalogo.estados.observe(this){
            if(verificaciones.listaNoVacia(it))
                binding.itemsEstado.setAdapter(ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it?.toList()!!
                ))
            else mostrarMensaje("No se pudieron obtener los estados, por favor inténte más tarde")
        }

        catalogo.municipios.observe(this){
            if(verificaciones.listaNoVacia(it))
                binding.itemsMunicipio.setAdapter(ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it?.toList()!!
                ))
            else mostrarMensaje("No se pudieron obtener los municipios, por favor inténte más tarde")
        }

        ciudad.ciudades.observe(this){
            if(verificaciones.listaNoVacia(it))
                binding.itemsCiudad.setAdapter(ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    it?.toList()!!
                ))
            else mostrarMensaje("No se pudieron obtener las ciudades, por favor inténte más tarde")
        }

        catalogo.getEstados()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentCrearCuentaDomicilioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    private fun mostrarMensaje(contenido: String) =
        mensaje.mostrarMensaje(parentFragmentManager, Constantes.Mensajes.ERROR, contenido)

    companion object{
        @JvmStatic
        fun newInstance(cliente: String, fotoByteArray: ByteArray) =
            CrearCuentaDomicilio().apply{
                arguments = Bundle().apply{
                    putString(Constantes.Utileria.CLIENTE, cliente)
                    putByteArray(Constantes.Utileria.FOTO, fotoByteArray)
                }
            }
    }
}