package uv.tc.appcuponsmart.controller.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.databinding.ItemSucursalBinding
import uv.tc.appcuponsmart.ui.view.activity.DetallePromocion

class SucursalesAdapter(
    private val sucursales: MutableList<Sucursal>,
    private val observador: DetallePromocion
): RecyclerView.Adapter<SucursalesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemSucursalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
    ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val sucursal = sucursales[position]

        holder.binding.txtNombre.text = sucursal.nombre
        holder.binding.txtDireccion.text = sucursal.direccion

        holder.binding.item.setOnClickListener{
            observador.seleccionarItem(sucursal)
        }
    }

    override fun getItemCount(): Int =
        sucursales.size

    class ViewHolder(val binding: ItemSucursalBinding): RecyclerView.ViewHolder(binding.root)
}