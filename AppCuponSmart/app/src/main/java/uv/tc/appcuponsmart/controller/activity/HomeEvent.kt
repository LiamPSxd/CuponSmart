package uv.tc.appcuponsmart.controller.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.activity.Home
import uv.tc.appcuponsmart.ui.view.activity.Perfil

class HomeEvent(
    private val activity: Home
): View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener{
    private val promociones = mutableListOf<Promocion>()
    private val promocionesCategoria = mutableListOf<Promocion>()

    override fun onClick(v: View?){
        when(v?.id){
            activity.binding.foto.id -> irPerfil()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
        val busqueda = s?.toString()?.lowercase()

        if(activity.verificaciones.cadena(busqueda)){
            val cuponesFiltrados = promocionesCategoria.filter{ promo ->
                promo.empresa?.lowercase()?.contains(busqueda!!) == true ||
                promo.vigencia?.lowercase()?.contains(busqueda!!) == true
            }

            activity.cupones.clear()
            activity.cupones.addAll(cuponesFiltrados)
            notificarAdapter()
        }else{
            activity.cupones.clear()
            activity.cupones.addAll(promocionesCategoria)
            notificarAdapter()
        }
    }

    override fun afterTextChanged(s: Editable?){}

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
        val idCategoria = (parent?.getItemAtPosition(position) as Categoria).id ?: 0

        if(activity.verificaciones.numerico(idCategoria)){
            val cuponesFiltrados = promociones.filter{ promo ->
                promo.idCategoria?.equals(idCategoria) == true
            }

            promocionesCategoria.clear()
            promocionesCategoria.addAll(cuponesFiltrados)

            activity.cupones.clear()
            activity.cupones.addAll(promocionesCategoria)
            notificarAdapter()
        }else{
            promocionesCategoria.clear()
            promocionesCategoria.addAll(promociones)

            activity.cupones.clear()
            activity.cupones.addAll(promocionesCategoria)
            notificarAdapter()
        }
    }

    private fun irPerfil() =
        activity.startActivity(Intent(activity, Perfil::class.java)
            .putExtra(Constantes.DataStore.ID_CLIENTE, activity.intent.extras?.getInt(Constantes.DataStore.ID_CLIENTE))
    )

    private fun notificarAdapter(){
        activity.apply{
            binding.recyclerView.adapter?.notifyDataSetChanged()
            binding.noItems.visibility = if(verificaciones.listaNoVacia(cupones)) View.INVISIBLE else View.VISIBLE
        }
    }

    fun inicializarPromociones(promociones: MutableList<Promocion>){
        this.promociones.clear()
        this.promociones.addAll(promociones)

        promocionesCategoria.clear()
        promocionesCategoria.addAll(promociones)
    }
}