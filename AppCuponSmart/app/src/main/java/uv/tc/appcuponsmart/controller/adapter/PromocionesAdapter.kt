package uv.tc.appcuponsmart.controller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

        holder.binding.txtCantidad.text = promocion.numeroCupones.toString()
        holder.binding.txtNombre.text = promocion.nombre
        holder.binding.txtEmpresa.text = promocion.empresa
        holder.binding.txtValorTipo.text = promocion.valorTipo
        holder.binding.txtVigencia.text = promocion.vigencia

        observador.media.imagenPromocion.observe(observador){
            it?.let{ imagen64 ->
                holder.binding.imagen.setImageBitmap(observador.verificaciones.base64ToBitMap(imagen64))
            }
        }

        observador.media.getImagenPromocion(promocion.id!!)

        holder.binding.item.setOnClickListener{
            observador.seleccionarItem(promocion)
        }
    }

    override fun getItemCount(): Int =
        promociones.size

    class ViewHolder(val binding: ItemPromocionBinding): RecyclerView.ViewHolder(binding.root)
}