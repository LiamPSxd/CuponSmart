package uv.tc.appcuponsmart.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.controller.adapter.SucursalesAdapter
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.data.model.interfaz.NotificarClicSucursal
import uv.tc.appcuponsmart.databinding.ActivityDetallePromocionBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.view.DetallePromocionViewModel
import javax.inject.Inject

@AndroidEntryPoint
class DetallePromocion: AppCompatActivity(), NotificarClicSucursal{
    private lateinit var binding: ActivityDetallePromocionBinding

    private val viewModel: DetallePromocionViewModel by viewModels()

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePromocionBinding.inflate(layoutInflater).apply{
            setContentView(root)

            recyclerView.layoutManager = LinearLayoutManager(this@DetallePromocion)
            recyclerView.setHasFixedSize(true)

            viewModel.error.observe(this@DetallePromocion){ error ->
                error?.let{ err ->
                    mostrarMensajeError(err)
                }
            }

            viewModel.promocionJSON.observe(this@DetallePromocion){ promocionJSON ->
                if(verificaciones.cadena(promocionJSON))
                    viewModel.setPromocion(json.fromJson(promocionJSON, Promocion::class.java))
                else viewModel.setError(Constantes.Excepciones.DATOS_PROMOCION)
            }

            viewModel.promocion.observe(this@DetallePromocion){ promocion ->
                promocion?.let{ promo ->
                    txtNombre.text = promo.nombre
                    txtCantidad.text = promo.numeroCupones.toString()
                    txtEmpresa.text = promo.empresa
                    txtValorTipo.text = promo.valorTipo
                    txtCodigo.text = promo.codigo
                    txtVigencia.text = promo.vigencia

                    txtDescripcion.text = promo.descripcion
                    txtRestricciones.text = promo.restricciones

                    if(verificaciones.cadena(promo.imagenBase64))
                        imagen.setImageBitmap(verificaciones.base64ToBitMap(promo.imagenBase64!!))
                    else
                        imagen.setImageDrawable(AppCompatResources.getDrawable(this@DetallePromocion, R.drawable.promocion))

                    viewModel.setSucursales(promo.sucursales ?: mutableListOf())
                }
            }

            viewModel.carga.observe(this@DetallePromocion){
                carga.visibility = if(it) View.VISIBLE else View.GONE
            }

            viewModel.sucursales.observe(this@DetallePromocion){ sucursales ->
                if(verificaciones.listaNoVacia(sucursales)){
                    noItems.visibility = View.INVISIBLE

                    recyclerView.adapter = SucursalesAdapter(sucursales!!, this@DetallePromocion)
                }else noItems.visibility = View.VISIBLE
            }

            recuperarPromocion()
        }
    }

    private fun recuperarPromocion() =
        viewModel.getPromocion()

    private fun mostrarMensajeError(contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, Constantes.Mensajes.ERROR, contenido)

    override fun seleccionarItem(sucursal: Sucursal){
        viewModel.putSucursal(json.toJson(sucursal))

        startActivity(Intent(this, DetalleSucursal::class.java))
    }
}