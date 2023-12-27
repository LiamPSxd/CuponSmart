package uv.tc.appcuponsmart.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.controller.fragment.CrearCuentaEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.ClienteViewModel
import uv.tc.appcuponsmart.ui.viewmodel.DireccionViewModel
import uv.tc.appcuponsmart.ui.viewmodel.MediaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CrearCuenta: Fragment(){
    private var _binding: FragmentCrearCuentaBinding? = null
    private val binding get() = _binding!!

    val cliente: ClienteViewModel by viewModels()
    val media: MediaViewModel by viewModels()
    val direccion: DireccionViewModel by viewModels()

    private lateinit var event: CrearCuentaEvent
    var cli: Cliente? = null
    var fotoByteArray: ByteArray? = byteArrayOf()
    var direc: Direccion? = null
    var correos = mutableListOf<String>()

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        arguments?.let{
            cli = json.fromJson(it.getString(Constantes.Utileria.CLIENTE), Cliente::class.java)
            fotoByteArray = it.getByteArray(Constantes.Utileria.FOTO)
            direc = json.fromJson(it.getString(Constantes.Utileria.DIRECCION), Direccion::class.java)
        }

        event = CrearCuentaEvent(this, binding)

        binding.btnCrearCuenta.setOnClickListener(event)

        cliente.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        media.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        direccion.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        cliente.clientes.observe(this){
            if(verificaciones.listaNoVacia(it)){
                it?.forEach{ cliente ->
                    correos.add(cliente.correo!!)
                }
            }
        }

        cliente.status.observe(this){
            if(it){
                cliente.getCliente(cli?.correo.toString())

                if(verificaciones.numerico(cli?.id!!)) {
                    media.addFotoCliente(cli?.id!!, fotoByteArray!!)
                }

                mostrarMensaje(Constantes.Mensajes.EXITO, Constantes.Mensajes.bienvenida(cliente.cliente.value?.nombre.toString()))
            }
            else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al registrar la cuenta, por favor inténte más tarde")
        }

        media.status.observe(this){
            if(!it) mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al registrar la foto, por favor inténte más tarde")
        }

        direccion.status.observe(this){
            if(it) direccion.getDirecciones()
            else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al registrar la dirección, por favor inténte más tarde")
        }

        direccion.direcciones.observe(this){
            if(verificaciones.listaNoVacia(it)){
                direc?.id = it?.filter{ dir ->
                    direc?.calle?.equals(dir.calle) == true &&
                    direc?.colonia?.equals(dir.colonia) == true &&
                    direc?.numero?.equals(dir.numero) == true &&
                    direc?.codigoPostal?.equals(dir.codigoPostal) == true &&
                    direc?.idCiudad?.equals(dir.idCiudad) == true
                }?.get(0)?.id ?: 0
            }
        }

        cliente.cliente.observe(this){
            it?.let{
                cli?.id = it.id
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentCrearCuentaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    fun mostrarMensaje(titulo: String, contenido: String) =
        mensaje.mostrarMensaje(parentFragmentManager, titulo, contenido)

    companion object{
        @JvmStatic
        fun newInstance(cliente: String, fotoByteArray: ByteArray, direccion: String) =
            CrearCuenta().apply{
                arguments = Bundle().apply{
                    putString(Constantes.Utileria.CLIENTE, cliente)
                    putByteArray(Constantes.Utileria.FOTO, fotoByteArray)
                    putString(Constantes.Utileria.DIRECCION, direccion)
                }
            }
    }
}