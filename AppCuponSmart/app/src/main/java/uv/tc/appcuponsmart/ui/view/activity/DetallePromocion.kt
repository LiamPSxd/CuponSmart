package uv.tc.appcuponsmart.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.controller.adapter.SucursalesAdapter
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.data.model.interfaz.NotificarClicSucursal
import uv.tc.appcuponsmart.databinding.ActivityDetallePromocionBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.DireccionViewModel
import uv.tc.appcuponsmart.ui.viewmodel.MediaViewModel
import uv.tc.appcuponsmart.ui.viewmodel.PromocionSucursalViewModel
import uv.tc.appcuponsmart.ui.viewmodel.PromocionViewModel
import uv.tc.appcuponsmart.ui.viewmodel.SucursalViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetallePromocion: AppCompatActivity(), NotificarClicSucursal{
    private lateinit var binding: ActivityDetallePromocionBinding

    private val promocion: PromocionViewModel by viewModels()
    private val sucursal: SucursalViewModel by viewModels()
    private val direccion: DireccionViewModel by viewModels()
    private val promocionSucursal: PromocionSucursalViewModel by viewModels()
    private val media: MediaViewModel by viewModels()

    private var sucursales = mutableListOf<Sucursal>()

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePromocionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        promocion.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

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

        promocionSucursal.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        media.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        promocion.promocion.observe(this){
            it?.let{
                it.id?.let{ id ->
                    promocionSucursal.getPromocionesSucursalesPorPromocion(id)
                    media.getImagenPromocion(id)
                }

                binding.txtNombre.text = it.nombre
                binding.txtCantidad.text = it.numeroCupones.toString()
                binding.txtEmpresa.text = it.empresa
                binding.txtValorTipo.text = it.valorTipo
                binding.txtCodigo.text = it.codigo
                binding.txtVigencia.text = it.vigencia

                binding.txtDescripcion.text = it.descripcion
                binding.txtRestricciones.text = it.restricciones
            }
        }

        media.imagenPromocion.observe(this){
            it?.let{ imagen64 ->
                binding.imagen.setImageBitmap(verificaciones.base64ToBitMap(imagen64))
            }
        }

        promocionSucursal.promocionesSucursales.observe(this){
            if(verificaciones.listaNoVacia(it)){
                binding.noItems.visibility = View.INVISIBLE

                it?.forEach{ promoSucur ->
                    sucursal.getSucursal(promoSucur.idSucursal!!)
                }
            }else binding.noItems.visibility = View.VISIBLE
        }

        sucursal.sucursal.observe(this){
            it?.let{
                direccion.direccion.observe(this){ dir ->
                    dir?.let{ d ->
                        it.direccion = "${d.calle}, ${d.numero}, ${d.colonia}"
                    }
                }

                direccion.getDireccion(it.idDireccion!!)

                sucursales.add(it)
            }
        }

        recuperarPromocion()

        binding.recyclerView.adapter = SucursalesAdapter(sucursales, this)
    }

    private fun recuperarPromocion(){
        val promo = intent.extras?.getString("promocion")

        if(verificaciones.cadena(promo))
            promocion.promocion.postValue(json.fromJson(promo, Promocion::class.java))
        else mostrarMensaje(Constantes.Excepciones.DATOS_PROMOCION)
    }

    private fun mostrarMensaje(contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, Constantes.Mensajes.ERROR, contenido)

    override fun seleccionarItem(sucursal: Sucursal) =
        startActivity(Intent(this, DetalleSucursal::class.java)
            .putExtra("sucursal", json.toJson(sucursal))
    )
}