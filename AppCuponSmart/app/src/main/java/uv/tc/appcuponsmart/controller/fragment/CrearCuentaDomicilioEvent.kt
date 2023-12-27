package uv.tc.appcuponsmart.controller.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaDomicilioBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuenta
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaDomicilio

class CrearCuentaDomicilioEvent(
    private val fragment: CrearCuentaDomicilio,
    private val binding: FragmentCrearCuentaDomicilioBinding,
    private val savedInstanceState: Bundle?
): View.OnClickListener, AdapterView.OnItemSelectedListener{
    private var idCiudad = 0

    override fun onClick(v: View?){
        when(v?.id){
            binding.btnContinuar.id -> irCrearCuenta()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
        val item = parent?.getItemAtPosition(position)

        when(view?.id){
            binding.itemsEstado.id -> obtenerMunicipios((item as Estado).id!!)
            binding.itemsMunicipio.id -> obtenerCiudades((item as Municipio).id!!)
            binding.itemsCiudad.id -> idCiudad = (item as Ciudad).id!!
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?){}

    private fun validarCampos(): Boolean{
        var res = true

        val calle = binding.txtCalle.text.toString()
        val colonia = binding.txtColonia.text.toString()
        val numero = binding.txtNumero.text.toString()
        val codigoPostal = binding.txtCodigoPostal.text.toString()
        val estado = binding.itemsEstado.isSelected
        val municipio = binding.itemsMunicipio.isSelected
        val ciudad = binding.itemsCiudad.isSelected

        if(!fragment.verificaciones.cadena(calle)){
            res = false
            binding.etqCalle.error = "La calle es obligatoria"
        }

        if(!fragment.verificaciones.cadena(colonia)){
            res = false
            binding.etqColonia.error = "La colonia es obligatoria"
        }

        if(!fragment.verificaciones.cadena(numero)){
            res = false
            binding.etqNumero.error = "El número es obligatorio"
        }

        if(!fragment.verificaciones.cadena(codigoPostal)){
            res = false
            binding.etqCodigoPostal.error = "El ccp es obligatorio"
        }else if(codigoPostal.length != 5){
            res = false
            binding.etqCodigoPostal.error = "El ccp debe tener 5 dígitos"
        }

        if(!estado){
            res = false
            binding.comboEstado.error = "Debe seleccionar un estado"
        }

        if(!municipio){
            res = false
            binding.comboMunicipio.error = "Debe seleccionar un municipio"
        }

        if(!ciudad){
            res = false
            binding.comboCiudad.error = "Debe seleccionar una ciudad"
        }

        return res
    }

    private fun irCrearCuenta(){
        if(validarCampos()){
            val direccion = Direccion(
                calle = binding.txtCalle.text.toString(),
                numero = binding.txtNumero.text.toString(),
                codigoPostal = binding.txtCodigoPostal.text.toString(),
                colonia = binding.txtColonia.text.toString(),
                idCiudad = idCiudad
            )

            if(fragment.verificaciones.isClassNull(savedInstanceState))
                fragment.parentFragmentManager.commit{
                    setReorderingAllowed(true)
                    replace<CrearCuenta>(R.id.contenedorFragment, args = bundleOf(
                        Constantes.Utileria.CLIENTE to fragment.cliente,
                        Constantes.Utileria.FOTO to fragment.fotoByteArray,
                        Constantes.Utileria.DIRECCION to fragment.json.toJson(direccion)
                    ))
                }
        }
    }

    private fun obtenerMunicipios(idEstado: Int) =
        fragment.catalogo.getMunicipiosPorEstado(idEstado)

    private fun obtenerCiudades(idMunicipio: Int) =
        fragment.ciudad.getCiudadesPorMunicipio(idMunicipio)
}