package uv.tc.appcuponsmart.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.controller.fragment.CrearCuentaEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.view.activity.IniciarSesion
import uv.tc.appcuponsmart.ui.viewmodel.view.CrearCuentaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CrearCuenta: Fragment(){
    private var _binding: FragmentCrearCuentaBinding? = null
    val binding get() = _binding!!

    val viewModel: CrearCuentaViewModel by activityViewModels()

    private lateinit var event: CrearCuentaEvent

    private var fotoByteArray = byteArrayOf()
    private var show = true
    private var correo = ""
    var idCliente = 0

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
        _binding = FragmentCrearCuentaBinding.inflate(inflater, container, false)

        event = CrearCuentaEvent(this)

        binding.apply{
            btnCrearCuenta.setOnClickListener(event)

            viewModel.error.observe(viewLifecycleOwner){ error ->
                error?.let{ err ->
                    mostrarMensaje(Constantes.Mensajes.ERROR, err)
                }
            }

            viewModel.idCliente.observe(viewLifecycleOwner){ idCliente ->
                if(verificaciones.numerico(idCliente)){
                    this@CrearCuenta.idCliente = idCliente

                    if(show)
                        mostrarMensaje(Constantes.Mensajes.ERROR, "El correo ya está registrado")
                    else lifecycleScope.launch(Dispatchers.Main){
                        async{
                            if(fotoByteArray.isNotEmpty())
                                async{ viewModel.addFotoCliente(idCliente, fotoByteArray) }.await()

                            viewModel.putIdCliente(idCliente)
                        }.await()

                        startActivity(Intent(requireContext(), IniciarSesion::class.java)
                            .putExtra(Constantes.DataStore.CORREO_CLIENTE, correo)
                        )
                        activity?.finishAffinity()
                        activity?.finish()
                    }
                }else
                    viewModel.addDireccion(viewModel.direccion.value!!)
            }

            viewModel.statusDireccion.observe(viewLifecycleOwner){ statusDireccion ->
                if(statusDireccion)
                    viewModel.getDirecciones()
                else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al registrar la dirección, por favor inténte más tarde")
            }

            viewModel.direcciones.observe(viewLifecycleOwner){ direcciones ->
                if(verificaciones.listaNoVacia(direcciones)){
                    val direccion = viewModel.direccion.value!!

                    val idDireccion = direcciones?.filter{ dir ->
                        direccion.calle?.equals(dir.calle) == true &&
                        direccion.colonia?.equals(dir.colonia) == true &&
                        direccion.numero?.equals(dir.numero) == true &&
                        direccion.codigoPostal?.equals(dir.codigoPostal) == true &&
                        direccion.idCiudad?.equals(dir.idCiudad) == true
                    }?.let{
                        it[0].id ?: 0
                    }

                    viewModel.cliente.value?.let{ cliente ->
                        cliente.correo = txtCorreo.text.toString()
                        cliente.contrasenia = txtContrasenia.text.toString()
                        cliente.idDireccion = idDireccion

                        viewModel.setCliente(cliente)
                    }
                }else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un problema hay crear su cuenta, por favor inténtelo más tarde")
            }

            viewModel.cliente.observe(viewLifecycleOwner){ cliente ->
                cliente?.let{
                    if(cliente.correo?.isNotEmpty() == true){
                        idCliente = 0
                        fotoByteArray = verificaciones.base64ToByteArray(cliente.fotoBase64 ?: "")

                        viewModel.putNombreCliente("${cliente.nombre}, 1")
                        viewModel.addCliente(cliente)
                    }
                }
            }

            viewModel.statusCliente.observe(viewLifecycleOwner){ statusCliente ->
                if(statusCliente){
                    lifecycleScope.launch(Dispatchers.Main){
                        show = false
                        correo = viewModel.cliente.value?.correo!!

                        viewModel.getIdCliente(correo)
                    }
                }else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al registrar la cuenta, por favor inténte más tarde")
            }

            viewModel.statusMedia.observe(viewLifecycleOwner){ statusMedia ->
                if(!statusMedia) mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al registrar la foto, por favor inténte más tarde")
            }

            return root
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    fun mostrarMensaje(titulo: String, contenido: String) =
        mensaje.mostrarMensaje(parentFragmentManager, titulo, contenido)

    companion object{
        @JvmStatic
        fun newInstance() = CrearCuenta()
    }
}