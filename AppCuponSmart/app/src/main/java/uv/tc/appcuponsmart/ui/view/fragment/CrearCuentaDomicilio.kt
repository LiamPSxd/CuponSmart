package uv.tc.appcuponsmart.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.controller.fragment.CrearCuentaDomicilioEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaDomicilioBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.view.CrearCuentaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CrearCuentaDomicilio: Fragment(){
    private var _binding: FragmentCrearCuentaDomicilioBinding? = null
    val binding get() = _binding!!

    val viewModel: CrearCuentaViewModel by activityViewModels()

    private lateinit var event: CrearCuentaDomicilioEvent

    var idCiudad = 0

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentCrearCuentaDomicilioBinding.inflate(inflater, container, false)

        event = CrearCuentaDomicilioEvent(this)

        binding.apply{
            btnContinuar.setOnClickListener(event)

            itemsEstado.setOnItemClickListener{ parent, _, position, _ ->
                itemsMunicipio.text.clear()
                itemsCiudad.text.clear()

                val idEstado = (parent.getItemAtPosition(position) as Estado).id ?: 0
                viewModel.getMunicipiosPorEstado(idEstado)
            }

            itemsMunicipio.setOnItemClickListener{ parent, _, position, _ ->
                itemsCiudad.text.clear()

                val idMunicipio = (parent.getItemAtPosition(position) as Municipio).id ?: 0
                viewModel.getCiudadesPorMunicipio(idMunicipio)
            }

            itemsCiudad.setOnItemClickListener{ parent, _, position, _ ->
                idCiudad = (parent.getItemAtPosition(position) as Ciudad).id ?: 0
            }

            viewModel.error.observe(viewLifecycleOwner){ error ->
                error?.let{ err ->
                    mostrarMensajeError(err)
                }
            }

            viewModel.estados.observe(viewLifecycleOwner){ estados ->
                if(verificaciones.listaNoVacia(estados))
                    itemsEstado.setAdapter(ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        estados?.toList()!!
                    ))
                else viewModel.setError(Constantes.Errores.COMBO_ESTADOS)
            }

            viewModel.municipios.observe(viewLifecycleOwner){ municipios ->
                if(verificaciones.listaNoVacia(municipios))
                    itemsMunicipio.setAdapter(ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        municipios?.toList()!!
                    ))
                else viewModel.setError(Constantes.Errores.COMBO_MUNICIPIOS)
            }

            viewModel.ciudades.observe(viewLifecycleOwner){ ciudades ->
                if(verificaciones.listaNoVacia(ciudades))
                    itemsCiudad.setAdapter(ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        ciudades?.toList()!!
                    ))
                else viewModel.setError(Constantes.Errores.COMBO_CIUDADES)
            }

            viewModel.direccion.observe(viewLifecycleOwner){
                if(!verificaciones.isClassNull(savedInstanceState))
                    parentFragmentManager.commit{
                        setReorderingAllowed(true)

                        replace<CrearCuenta>(R.id.contenedorFragment)
                    }
            }

            viewModel.getEstados()

            return root
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    private fun mostrarMensajeError(contenido: String) =
        mensaje.mostrarMensaje(parentFragmentManager, Constantes.Mensajes.ERROR, contenido)

    companion object{
        @JvmStatic
        fun newInstance() = CrearCuentaDomicilio()
    }
}