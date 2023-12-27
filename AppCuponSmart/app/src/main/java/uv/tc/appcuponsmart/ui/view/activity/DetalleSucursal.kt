package uv.tc.appcuponsmart.ui.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.databinding.ActivityDetalleSucursalBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.CatalogoViewModel
import uv.tc.appcuponsmart.ui.viewmodel.CiudadViewModel
import uv.tc.appcuponsmart.ui.viewmodel.DireccionViewModel
import uv.tc.appcuponsmart.ui.viewmodel.SucursalViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetalleSucursal: AppCompatActivity(), OnMapReadyCallback{
    private lateinit var binding: ActivityDetalleSucursalBinding

    private val sucursal: SucursalViewModel by viewModels()
    private val direccion: DireccionViewModel by viewModels()
    private val ciudad: CiudadViewModel by viewModels()
    private val catalogo: CatalogoViewModel by viewModels()

    private lateinit var map: GoogleMap

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleSucursalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sucursal.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        direccion.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        ciudad.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        catalogo.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        sucursal.sucursal.observe(this){
            it?.let{
                binding.txtNombre.text = it.nombre
                binding.txtTelefono.text = it.telefono
                binding.txtNombreEncargado.text = it.nombreEncargado

                direccion.getDireccion(it.idDireccion!!)
            }
        }

        direccion.direccion.observe(this){
            it?.let{
                binding.txtDireccion.text = "${it.calle}\n${it.numero}, ${it.codigoPostal}\n${it.colonia}"

                ciudad.getCiudad(it.idCiudad!!)
            }
        }

        ciudad.ciudad.observe(this){
            it?.let{
                binding.txtUbicacion.text = it.nombre

                catalogo.getMunicipio(it.idMunicipio!!)
            }
        }

        catalogo.municipio.observe(this){
            it?.let{
                binding.txtUbicacion.text = "${binding.txtUbicacion.text}, ${it.nombre}"

                catalogo.getEstado(it.idEstado!!)
            }
        }

        catalogo.estado.observe(this){
            it?.let{
                binding.txtUbicacion.text = "${binding.txtUbicacion.text}, ${it.nombre}"
            }
        }

        recuperarSucursal()
        crearMapa()
    }

    private fun recuperarSucursal(){
        val sucur = intent.extras?.getString("sucursal")

        if(verificaciones.cadena(sucur))
            sucursal.sucursal.postValue(json.fromJson(sucur, Sucursal::class.java))
        else mostrarMensaje(Constantes.Excepciones.DATOS_SUCURSAL)
    }

    private fun crearMapa(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun crearMarcador(){
        direccion.direccion.value?.let{
            val coordenadas = LatLng(it.latitud?.toDouble()!!, it.longitud?.toDouble()!!)

            map.addMarker(
                MarkerOptions().position(coordenadas).title(sucursal.sucursal.value?.nombre)
            )
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(coordenadas, 18f),
                3000,
                null
            )
        }
    }

    override fun onMapReady(googleMaps: GoogleMap){
        map = googleMaps
        crearMarcador()
    }

    private fun mostrarMensaje(contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, Constantes.Mensajes.ERROR, contenido)
}