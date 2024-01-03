package uv.tc.appcuponsmart.controller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.databinding.ItemPromocionBinding
import uv.tc.appcuponsmart.ui.view.activity.Home

class PromocionesAdapter(
    private val promociones: MutableList<Promocion>,
    private val observador: Home
): RecyclerView.Adapter<PromocionesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemPromocionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
    ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val promocion = promociones[position]

        holder.binding.apply{
            txtCantidad.text = promocion.numeroCupones.toString()
            txtNombre.text = promocion.nombre
            txtEmpresa.text = promocion.empresa
            txtValorTipo.text = promocion.valorTipo
            txtVigencia.text = promocion.vigencia

            if(observador.verificaciones.cadena(promocion.imagenBase64))
                imagen.setImageBitmap(observador.verificaciones.base64ToBitMap(promocion.imagenBase64!!))
            else
                imagen.setImageDrawable(AppCompatResources.getDrawable(observador, R.drawable.promocion))

            item.setOnClickListener{
                observador.seleccionarItem(promocion)
            }
        }
    }

    override fun getItemCount(): Int =
        promociones.size

    class ViewHolder(val binding: ItemPromocionBinding): RecyclerView.ViewHolder(binding.root)
}