package uv.tc.appcuponsmart.ui.view.activity

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.controller.activity.PerfilEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.databinding.ActivityPerfilBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.CatalogoViewModel
import uv.tc.appcuponsmart.ui.viewmodel.CiudadViewModel
import uv.tc.appcuponsmart.ui.viewmodel.ClienteViewModel
import uv.tc.appcuponsmart.ui.viewmodel.DireccionViewModel
import uv.tc.appcuponsmart.ui.viewmodel.MediaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class Perfil: AppCompatActivity(){
    private lateinit var binding: ActivityPerfilBinding

    val cliente: ClienteViewModel by viewModels()
    val direccion: DireccionViewModel by viewModels()
    val ciudad: CiudadViewModel by viewModels()
    val catalogo: CatalogoViewModel by viewModels()
    val media: MediaViewModel by viewModels()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "almacenamiento")

    private lateinit var event: PerfilEvent
    var idCiudad = 0

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        event = PerfilEvent(this, binding, dataStore)

        binding.btnAceptar.setOnClickListener(event)
        binding.btnCancelar.setOnClickListener(event)
        binding.btnModificar.setOnClickListener(event)
        binding.btnCerrarSesion.setOnClickListener(event)
        binding.btnCambiarFoto.setOnClickListener(event)
        binding.etqFechaNacimiento.setOnClickListener(event)
        binding.itemsEstado.onItemSelectedListener = event
        binding.itemsMunicipio.onItemSelectedListener = event
        binding.itemsCiudad.onItemSelectedListener = event

        cliente.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        direccion.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        ciudad.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        catalogo.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        media.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        cliente.cliente.observe(this){
            it?.let{ cl ->
                media.getFotoCliente(cl.id!!)
                binding.txtNombre.setText(cl.nombre)
                binding.txtApellidoPaterno.setText(cl.apellidoPaterno)
                binding.txtApellidoMaterno.setText(cl.apellidoMaterno)
                binding.txtTelefono.setText(cl.telefono)
                binding.txtFechaNacimiento.setText(cl.fechaNacimiento)

                direccion.getDireccion(cl.idDireccion!!)

                binding.txtCorreo.setText(cl.correo)
                binding.txtContrasenia.setText(cl.contrasenia)
            }
        }

        media.fotoCliente.observe(this){
            it?.let{ foto64 ->
                binding.foto.setImageBitmap(verificaciones.base64ToBitMap(foto64))
            }
        }

        direccion.direccion.observe(this){
            it?.let{ direccion ->
                binding.txtCalle.setText(direccion.calle)
                binding.txtColonia.setText(direccion.colonia)
                binding.txtNumero.setText(direccion.numero)
                binding.txtCodigoPostal.setText(direccion.codigoPostal)

                ciudad.getCiudad(direccion.idCiudad!!)
            }
        }

        ciudad.ciudad.observe(this){
            it?.idMunicipio?.let{ idMunicipio ->
                catalogo.getMunicipio(idMunicipio)
                ciudad.getCiudadesPorMunicipio(idMunicipio)
            }

            it?.let{ cd ->
                idCiudad = cd.id!!

                ciudad.ciudades.value?.let{cds ->
                    binding.itemsCiudad.setSelection(cds.indexOf(cd))
                }
            }
        }

        catalogo.municipio.observe(this){
            it?.let{ municipio ->
                catalogo.getMunicipiosPorEstado(municipio.idEstado!!)

                catalogo.municipios.value?.let{ mps ->
                    binding.itemsMunicipio.setSelection(mps.indexOf(municipio))
                }

                for(i in catalogo.estados.value?.indices!!){
                    val ed = catalogo.estados.value?.get(i)

                    if(ed?.id == municipio.idEstado)
                        binding.itemsEstado.setSelection(i)
                }
            }
        }

        catalogo.estados.observe(this){
            if(verificaciones.listaNoVacia(it))
                binding.itemsEstado.setAdapter(ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    it?.toList()!!
                ))
            else mostrarMensaje(Constantes.Mensajes.ERROR, "No se pudieron obtener los estados, por favor inténte más tarde")
        }

        catalogo.municipios.observe(this){
            if(verificaciones.listaNoVacia(it))
                binding.itemsMunicipio.setAdapter(ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    it?.toList()!!
                ))
            else mostrarMensaje(Constantes.Mensajes.ERROR, "No se pudieron obtener los municipios, por favor inténte más tarde")
        }

        ciudad.ciudades.observe(this){
            if(verificaciones.listaNoVacia(it))
                binding.itemsCiudad.setAdapter(ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    it?.toList()!!
                ))
            else mostrarMensaje(Constantes.Mensajes.ERROR, "No se pudieron obtener las ciudades, por favor inténte más tarde")
        }

        direccion.status.observe(this){
            if(it)
                cliente.cliente.value?.let{ cl ->
                    cliente.updateCliente(cl)
                }
        }

        cliente.status.observe(this){
            if(it) mostrarMensaje(Constantes.Mensajes.EXITO, "Información actualizada exitosamente")
            else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al actualizar la información, por favor inténte más tarde")
        }

        media.status.observe(this){
            if(!it) mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al actualizar la foto, por favor inténte más tarde")
        }

        catalogo.getEstados()
        recuperarIdCliente()
    }

    fun recuperarIdCliente(){
        val idCliente = intent.extras?.getInt("idCliente") ?: 0

        if(verificaciones.numerico(idCliente))
            cliente.getCliente(idCliente)
        else mostrarMensaje(Constantes.Mensajes.ERROR, Constantes.Excepciones.DATOS_CLIENTE)
    }

    fun mostrarMensaje(titulo: String, contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, titulo, contenido)
}