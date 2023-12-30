package uv.tc.appcuponsmart.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.controller.activity.PerfilEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.databinding.ActivityPerfilBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.view.dialog.MensajeCerrarSesion
import uv.tc.appcuponsmart.ui.viewmodel.view.PerfilViewModel
import javax.inject.Inject

@AndroidEntryPoint
class Perfil: AppCompatActivity(), MensajeCerrarSesion.MensajeCerrarSesionListener{
    lateinit var binding: ActivityPerfilBinding

    val viewModel: PerfilViewModel by viewModels()

    private lateinit var event: PerfilEvent
    private var estadoItem = Estado()
    private var municipioItem = Municipio()
    var ciudadItem = Ciudad()
    var fotoByteArray = byteArrayOf()

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater).apply{
            setContentView(root)

            event = PerfilEvent(this@Perfil)

            btnAceptar.setOnClickListener(event)
            btnCancelar.setOnClickListener(event)
            btnModificar.setOnClickListener(event)
            btnCerrarSesion.setOnClickListener(event)
            btnCambiarFoto.setOnClickListener(event)
            txtFechaNacimiento.setOnClickListener(event)
            txtFechaNacimiento.inputType = InputType.TYPE_NULL

            itemsEstado.setOnItemClickListener{ parent, _, position, _ ->
                municipioItem.nombre = "Seleccione un municipio"
                ciudadItem.nombre = "Seleccione una ciudad"

                val idEstado = (parent.getItemAtPosition(position) as Estado).id ?: 0
                viewModel.getMunicipiosPorEstado(idEstado)
            }

            itemsMunicipio.setOnItemClickListener{ parent, _, position, _ ->
                ciudadItem.nombre = "Seleccione una ciudad"

                val idMunicipio = (parent.getItemAtPosition(position) as Municipio).id ?: 0
                viewModel.getCiudadesPorMunicipio(idMunicipio)
            }

            itemsCiudad.setOnItemClickListener{ parent, _, position, _ ->
                ciudadItem.id = (parent.getItemAtPosition(position) as Ciudad).id ?: 0
            }

            viewModel.error.observe(this@Perfil){ error ->
                error?.let{ err ->
                    mostrarMensaje(Constantes.Mensajes.ERROR, err)
                }
            }

            viewModel.fotoCliente.observe(this@Perfil){ media ->
                if(verificaciones.cadena(media)){
                    val bitmap = verificaciones.base64ToBitMap(media!!)
                    foto.setImageBitmap(bitmap)
                }else
                    foto.setImageDrawable(AppCompatResources.getDrawable(this@Perfil, R.drawable.usuario))
            }

            viewModel.cliente.observe(this@Perfil){ cliente ->
                cliente?.let{ cl ->
                    txtNombre.setText(cl.nombre)
                    txtApellidoPaterno.setText(cl.apellidoPaterno)
                    txtApellidoMaterno.setText(cl.apellidoMaterno)
                    txtTelefono.setText(cl.telefono)
                    txtFechaNacimiento.setText(cl.fechaNacimiento)

                    txtCorreo.setText(cl.correo)
                    txtContrasenia.setText(cl.contrasenia)

                    lifecycleScope.launch(Dispatchers.Main){
                        async{
                            async{ viewModel.getFotoCliente(cl.id ?: 0) }.await()

                            async{ viewModel.getDireccion(cl.idDireccion ?: 0) }.await()
                        }.await()
                    }
                }
            }

            viewModel.direccion.observe(this@Perfil){ direccion ->
                direccion?.let{ dir ->
                    txtCalle.setText(dir.calle)
                    txtColonia.setText(dir.colonia)
                    txtNumero.setText(dir.numero)
                    txtCodigoPostal.setText(dir.codigoPostal)

                    viewModel.getCiudad(dir.idCiudad ?: 0)
                }
            }

            viewModel.ciudad.observe(this@Perfil){ ciudad ->
                ciudad?.let{ cd ->
                    ciudadItem = cd

                    lifecycleScope.launch(Dispatchers.Main){
                        async{ viewModel.getMunicipio(cd.idMunicipio ?: 0) }.await()
                        async{ viewModel.getCiudadesPorMunicipio(cd.idMunicipio ?: 0) }.await()
                    }
                }
            }

            viewModel.municipio.observe(this@Perfil){ municipio ->
                municipio?.let{ muni ->
                    municipioItem = muni

                    lifecycleScope.launch(Dispatchers.Main){
                        async{ viewModel.getEstado(muni.idEstado ?: 0) }.await()
                        async{ viewModel.getMunicipiosPorEstado(muni.idEstado ?: 0) }.await()
                    }
                }
            }

            viewModel.estado.observe(this@Perfil){ estado ->
                estado?.let{ est ->
                    estadoItem = est

                    viewModel.getEstados()
                }
            }

            viewModel.estados.observe(this@Perfil){ estados ->
                if(verificaciones.listaNoVacia(estados)){
                    estadoItem.nombre?.let{
                        itemsEstado.setText(it)
                    }

                    itemsEstado.setAdapter(ArrayAdapter(
                        this@Perfil,
                        android.R.layout.simple_spinner_dropdown_item,
                        estados?.toList()!!
                    ))
                }else viewModel.setError("No se pudieron obtener los estados, por favor inténte más tarde")
            }

            viewModel.municipios.observe(this@Perfil){ municipios ->
                if(verificaciones.listaNoVacia(municipios)){
                    municipioItem.nombre?.let{
                        itemsMunicipio.setText(it)
                    }

                    itemsMunicipio.setAdapter(ArrayAdapter(
                        this@Perfil,
                        android.R.layout.simple_spinner_dropdown_item,
                        municipios?.toList()!!
                    ))
                }else viewModel.setError("No se pudieron obtener los municipios, por favor inténte más tarde")
            }

            viewModel.ciudades.observe(this@Perfil){ ciudades ->
                if(verificaciones.listaNoVacia(ciudades)){
                    ciudadItem.nombre?.let{
                        itemsCiudad.setText(it)
                    }

                    itemsCiudad.setAdapter(ArrayAdapter(
                        this@Perfil,
                        android.R.layout.simple_spinner_dropdown_item,
                        ciudades?.toList()!!
                    ))
                }else viewModel.setError("No se pudieron obtener las ciudades, por favor inténte más tarde")
            }

            viewModel.statusDireccion.observe(this@Perfil){ status ->
                if(status){
                    viewModel.cliente.value?.let{ cl ->
                        lifecycleScope.launch(Dispatchers.Main){
                            async{
                                if(fotoByteArray.isNotEmpty())
                                    async{ viewModel.addFotoCliente(cl.id!!, fotoByteArray) }.await()

                                async{ viewModel.updateCliente(cl) }.await()
                            }.await()
                        }
                    }
                }else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al actualizar la información de su dirección, por favor inténte más tarde")
            }

            viewModel.statusCliente.observe(this@Perfil){ status ->
                if(status){
                    event.habilitarComponentes(false)

                    mostrarMensaje(Constantes.Mensajes.EXITO, "Información actualizada exitosamente")
                }
                else mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al actualizar la información de su cuenta, por favor inténte más tarde")
            }

            viewModel.statusMedia.observe(this@Perfil){ status ->
                if(!status) mostrarMensaje(Constantes.Mensajes.ERROR, "Hubo un error al actualizar su foto, por favor inténte más tarde")
            }

            recuperarIdCliente()

            swipe.apply{
                setColorSchemeColors(
                    resources.getColor(R.color.naranjaMediumGreek, theme),
                    resources.getColor(R.color.splash, theme)
                )
                setOnRefreshListener{
                    lifecycleScope.launch(Dispatchers.Main){
                        async{
                            async{ recuperarIdCliente() }.await()

                            event.habilitarComponentes(false)
                        }.await()

                        isRefreshing = false
                    }
                }
            }
        }
    }

    fun recuperarIdCliente(){
        val idCliente = intent.extras?.getInt(Constantes.DataStore.ID_CLIENTE) ?: 0

        if(verificaciones.numerico(idCliente))
            viewModel.getCliente(idCliente)
        else mostrarMensaje(Constantes.Mensajes.ERROR, Constantes.Excepciones.DATOS_CLIENTE)
    }

    fun mostrarMensaje(titulo: String, contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, titulo, contenido)

    fun mostrarMensajeCerrarSesion(titulo: String, contenido: String) =
        MensajeCerrarSesion(titulo, contenido).show(supportFragmentManager, "MensajeCerrarSesion")

    override fun onDialogPositiveClick(dialog: DialogFragment){
        lifecycleScope.launch(Dispatchers.Main){
            async{ viewModel.deleteDataStore() }.await()

            startActivity(Intent(this@Perfil, IniciarSesion::class.java))
            finishAffinity()
            finish()
        }
    }
}