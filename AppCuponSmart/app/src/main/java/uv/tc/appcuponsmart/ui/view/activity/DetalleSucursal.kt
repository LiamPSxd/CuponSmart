package uv.tc.appcuponsmart.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.databinding.ActivityDetalleSucursalBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.view.DetalleSucursalViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetalleSucursal: AppCompatActivity(), OnMapReadyCallback{
    private lateinit var binding: ActivityDetalleSucursalBinding

    private val viewModel: DetalleSucursalViewModel by viewModels()

    private lateinit var map: GoogleMap
    private var latitud = 0.0
    private var longitud = 0.0

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleSucursalBinding.inflate(layoutInflater).apply{
            setContentView(root)

            ventana.setOnTouchListener{ _, event ->
                when(event.action){
                    MotionEvent.ACTION_DOWN -> {
                        scrollView.requestDisallowInterceptTouchEvent(true)
                        false
                    }

                    MotionEvent.ACTION_UP -> {
                        scrollView.requestDisallowInterceptTouchEvent(false)
                        true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        scrollView.requestDisallowInterceptTouchEvent(true)
                        false
                    }

                    else -> true
                }
            }

            viewModel.error.observe(this@DetalleSucursal){ error ->
                error?.let{ err ->
                    mostrarMensajeError(err)
                }
            }

            viewModel.sucursalJSON.observe(this@DetalleSucursal){ sucursalJSON ->
                if(verificaciones.cadena(sucursalJSON))
                    viewModel.setSucursal(json.fromJson(sucursalJSON, Sucursal::class.java))
                else viewModel.setError(Constantes.Excepciones.DATOS_SUCURSAL)
            }

            viewModel.sucursal.observe(this@DetalleSucursal){ sucursal ->
                sucursal?.let{ suc ->
                    txtNombre.text = suc.nombre
                    txtNombreEncargado.text = suc.nombreEncargado
                    txtTelefono.text = suc.telefono

                    txtDireccion.text = suc.direccion
                    txtUbicacion.text = suc.ubicacion

                    latitud = suc.coordenadas[Constantes.Utileria.LATITUD] ?: Constantes.Utileria.VALOR_LATITUD
                    longitud = suc.coordenadas[Constantes.Utileria.LONGITUD] ?: Constantes.Utileria.VALOR_LONGITUD
                }
            }

            lifecycleScope.launch(Dispatchers.Main){
                async{ recuperarSucursal() }.await()
                async{ crearMapa() }.await()

                crearMarcador()
            }
        }
    }

    private fun recuperarSucursal() =
        viewModel.getSucursal()

    private fun mostrarMensajeError(contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, Constantes.Mensajes.ERROR, contenido)

    private fun crearMapa(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun crearMarcador(){
        val coordenadas = LatLng(latitud, longitud)

        map.addMarker(
            MarkerOptions().position(coordenadas).title(binding.txtNombre.text.toString())
        )
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, 18f),
            3000,
            null
        )
    }

    override fun onMapReady(googleMaps: GoogleMap){
        map = googleMaps
    }
}