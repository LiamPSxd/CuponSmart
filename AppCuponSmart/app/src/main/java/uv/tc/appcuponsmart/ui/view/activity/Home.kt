package uv.tc.appcuponsmart.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.controller.activity.HomeEvent
import uv.tc.appcuponsmart.controller.adapter.PromocionesAdapter
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.model.interfaz.NotificarClicPromocion
import uv.tc.appcuponsmart.databinding.ActivityHomeBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.CatalogoViewModel
import uv.tc.appcuponsmart.ui.viewmodel.CategoriaViewModel
import uv.tc.appcuponsmart.ui.viewmodel.ClienteViewModel
import uv.tc.appcuponsmart.ui.viewmodel.EmpresaViewModel
import uv.tc.appcuponsmart.ui.viewmodel.MediaViewModel
import uv.tc.appcuponsmart.ui.viewmodel.PromocionViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class Home: AppCompatActivity(), NotificarClicPromocion{
    private lateinit var binding: ActivityHomeBinding

    private val cliente: ClienteViewModel by viewModels()
    private val categoria: CategoriaViewModel by viewModels()
    private val promocion: PromocionViewModel by viewModels()
    private val catalogo: CatalogoViewModel by viewModels()
    private val empresa: EmpresaViewModel by viewModels()
    val media: MediaViewModel by viewModels()

    private lateinit var event: HomeEvent
    var cupones = mutableListOf<Promocion>()

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        event = HomeEvent(this, binding)

        binding.foto.setOnClickListener(event)
        binding.busquedaView.setOnQueryTextListener(event)
        binding.itemsCategoria.onItemSelectedListener = event

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        cliente.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        categoria.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        promocion.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        catalogo.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        empresa.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        media.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(error)
            }
        }

        cliente.cliente.observe(this){
            it?.let{ cl ->
                media.getFotoCliente(cl.id!!)

                binding.usuario.text = "¡Hola ${cl.nombre}!"
            }
        }

        media.fotoCliente.observe(this){
            it?.let{ foto64 ->
                binding.foto.setImageBitmap(verificaciones.base64ToBitMap(foto64))
            }
        }

        categoria.categorias.observe(this){
            if(verificaciones.listaNoVacia(it))
                binding.itemsCategoria.setAdapter(ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    it?.toList()!!
                ))
            else mostrarMensaje("No se pudieron obtener las categorías, por favor inténte más tarde")
        }

        promocion.cupones.observe(this){
            if(verificaciones.listaNoVacia(it)){
                binding.noItems.visibility = View.INVISIBLE

                val formato = SimpleDateFormat(Constantes.Utileria.FORMATO_FECHA, Locale.US)

                it?.forEach{ promo ->
                    when(promo.tipo){
                        "Descuento" -> promo.valorTipo = "${promo.valor}% de ${promo.tipo}"
                        "Costo Rebajado" -> promo.valorTipo = "$${promo.valor} de ${promo.tipo}"
                    }

                    val fechaInicio = formato.parse(promo.fechaInicio.toString())
                    val fechaTermino = formato.parse(promo.fechaTermino.toString())

                    promo.vigencia = "$fechaInicio a $fechaTermino"

                    empresa.empresa.observe(this){ empresa ->
                        promo.empresa = empresa?.nombre
                    }

                    catalogo.tipoPromocion.observe(this){ tipoPromo ->
                        promo.tipo = tipoPromo?.tipo
                    }

                    empresa.getEmpresa(promo.idEmpresa!!)
                    catalogo.getTipoPromocion(promo.idTipoPromocion!!)
                }

                it?.let{ promos ->
                    binding.recyclerView.adapter = PromocionesAdapter(promos, this)
                    cupones = promos
                }
            }else binding.noItems.visibility = View.VISIBLE
        }

        recuperarIdCliente()
        categoria.getCategorias()
        promocion.getCupones()
    }

    private fun recuperarIdCliente(){
        val idCliente = intent.extras?.getInt("idCliente") ?: 0

        if(verificaciones.numerico(idCliente))
            cliente.getCliente(idCliente)
        else mostrarMensaje(Constantes.Excepciones.DATOS_CLIENTE)
    }

    private fun mostrarMensaje(contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, Constantes.Mensajes.ERROR, contenido)

    override fun seleccionarItem(promocion: Promocion) =
        startActivity(Intent(this, DetallePromocion::class.java)
            .putExtra("promocion", json.toJson(promocion))
    )
}