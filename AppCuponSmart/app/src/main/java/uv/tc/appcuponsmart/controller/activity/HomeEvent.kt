package uv.tc.appcuponsmart.controller.activity

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import uv.tc.appcuponsmart.controller.adapter.PromocionesAdapter
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.databinding.ActivityHomeBinding
import uv.tc.appcuponsmart.ui.view.activity.Home
import uv.tc.appcuponsmart.ui.view.activity.Perfil

class HomeEvent(
    private val activity: Home,
    private val binding: ActivityHomeBinding
): View.OnClickListener, SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener{
    private val promociones = mutableListOf<Promocion>()

    override fun onClick(v: View?){
        when(v?.id){
            binding.foto.id -> irPerfil()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean{
        binding.busquedaView.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean{
        val busqueda = newText?.lowercase()

        if(activity.verificaciones.cadena(busqueda)){
            promociones.forEach{ promo ->
                if(promo.empresa?.lowercase()?.contains(busqueda!!) == false ||
                    promo.vigencia?.lowercase()?.contains(busqueda!!) == false)
                    promociones.remove(promo)
            }

            iniciarAdapter(promociones)
        }else
            iniciarAdapter(activity.cupones)

        return false
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
        val idCategoria = (parent?.getItemAtPosition(position) as Categoria).id ?: 0
        promociones.clear()

        if(activity.verificaciones.numerico(idCategoria)){
            activity.cupones.forEach{ promo ->
                if(promo.idCategoria?.equals(idCategoria) == true)
                    promociones.add(promo)
            }

            iniciarAdapter(promociones)
        }else
            iniciarAdapter(activity.cupones)
    }

    override fun onNothingSelected(parent: AdapterView<*>?){}

    private fun irPerfil() =
        activity.startActivity(Intent(activity, Perfil::class.java)
            .putExtra("idCliente", activity.intent.extras?.getInt("idCliente"))
    )

    private fun iniciarAdapter(promociones: MutableList<Promocion>){
        binding.recyclerView.adapter = PromocionesAdapter(promociones, activity)
    }
}