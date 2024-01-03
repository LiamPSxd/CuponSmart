package uv.tc.appcuponsmart.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.controller.activity.HomeEvent
import uv.tc.appcuponsmart.controller.adapter.PromocionesAdapter
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.model.interfaz.NotificarClicPromocion
import uv.tc.appcuponsmart.databinding.ActivityHomeBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.view.HomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class Home: AppCompatActivity(), NotificarClicPromocion{
    lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var event: HomeEvent
    val cupones = mutableListOf<Promocion>()

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater).apply{
            setContentView(root)

            event = HomeEvent(this@Home)

            foto.setOnClickListener(event)
            txtBusqueda.addTextChangedListener(event)
            itemsCategoria.onItemClickListener = event

            recyclerView.layoutManager = LinearLayoutManager(this@Home)
            recyclerView.setHasFixedSize(true)

            viewModel.error.observe(this@Home){ error ->
                error?.let{ err ->
                    mostrarMensaje(Constantes.Mensajes.ERROR, err)
                }
            }

            viewModel.nombreCliente.observe(this@Home){ nombreCliente ->
                nombreCliente?.let{ nC ->
                    val variables = nC.split(", ")
                    val nombre = variables[0]
                    val accion = variables[1].toInt()

                    if(verificaciones.numerico(accion)){
                        viewModel.putNombreCliente("$nombre, 0")

                        mostrarMensaje("¡Hola!", Constantes.Mensajes.bienvenida(nombre))
                    }
                }
            }

            viewModel.fotoCliente.observe(this@Home){ media ->
                if(verificaciones.cadena(media)){
                    val bitmap = verificaciones.base64ToBitMap(media!!)
                    binding.foto.setImageBitmap(bitmap)
                }else
                    binding.foto.setImageDrawable(AppCompatResources.getDrawable(this@Home, R.drawable.usuario))
            }

            viewModel.cliente.observe(this@Home){ cliente ->
                cliente?.let{ cl ->
                    txtNombre.text = "¡Hola ${cl.nombre}!"

                    viewModel.getFotoCliente(cl.id ?: 0)
                }
            }

            viewModel.categorias.observe(this@Home){ categorias ->
                if(verificaciones.listaNoVacia(categorias)){
                    categorias?.add(Categoria(nombre = "Todas"))

                    itemsCategoria.setAdapter(ArrayAdapter(
                        this@Home,
                        android.R.layout.simple_spinner_dropdown_item,
                        categorias?.toList()!!
                    ))
                }else viewModel.setError("No se pudieron obtener las categorías, por favor inténte más tarde")
            }

            viewModel.carga.observe(this@Home){
                carga.visibility = if(it) View.VISIBLE else View.GONE
            }

            viewModel.promociones.observe(this@Home){ promociones ->
                if(verificaciones.listaNoVacia(promociones)){
                    cupones.clear()
                    cupones.addAll(promociones!!)

                    noItems.visibility = View.INVISIBLE

                    recyclerView.adapter = PromocionesAdapter(cupones, this@Home)
                    event.inicializarPromociones(cupones)
                }else noItems.visibility = View.VISIBLE
            }

            lifecycleScope.launch(Dispatchers.Main){
                async{ recuperarIdCliente() }.await()
                async{ viewModel.getCategorias() }.await()
                async{ viewModel.getCupones() }.await()
                async{ viewModel.getNombreCliente() }.await()
            }

            swipe.apply{
                setColorSchemeColors(
                    resources.getColor(R.color.naranjaMediumGreek, theme),
                    resources.getColor(R.color.splash, theme)
                )
                setOnRefreshListener{
                    lifecycleScope.launch(Dispatchers.Main){
                        async{
                            cupones.clear()
                            recyclerView.adapter?.notifyDataSetChanged()

                            noItems.visibility = View.INVISIBLE

                            async{ recuperarIdCliente() }.await()
                            async{ viewModel.getCategorias() }.await()
                            async{ viewModel.getCupones() }.await()
                        }.await()

                        isRefreshing = false
                    }
                }
            }
        }
    }

    private fun recuperarIdCliente(){
        val idCliente = intent.extras?.getInt(Constantes.DataStore.ID_CLIENTE) ?: 0

        if(verificaciones.numerico(idCliente))
            viewModel.getCliente(idCliente)
        else mostrarMensaje(Constantes.Mensajes.ERROR, Constantes.Excepciones.DATOS_CLIENTE)
    }

    private fun mostrarMensaje(titulo: String, contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, titulo, contenido)

    override fun seleccionarItem(promocion: Promocion){
        viewModel.putPromocion(json.toJson(promocion))

        startActivity(Intent(this, DetallePromocion::class.java))
    }
}