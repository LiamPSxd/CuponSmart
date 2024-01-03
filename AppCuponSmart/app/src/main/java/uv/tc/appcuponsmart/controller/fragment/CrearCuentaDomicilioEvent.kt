package uv.tc.appcuponsmart.controller.fragment

import android.view.View
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaDomicilio

class CrearCuentaDomicilioEvent(
    private val fragment: CrearCuentaDomicilio
): View.OnClickListener{
    override fun onClick(v: View?){
        when(v?.id){
            fragment.binding.btnContinuar.id -> irCrearCuenta()
        }
    }

    private fun validarCampos(): Boolean{
        fragment.binding.apply{
            var res = true

            val calle = txtCalle.text.toString()
            val colonia = txtColonia.text.toString()
            val numero = txtNumero.text.toString()
            val codigoPostal = txtCodigoPostal.text.toString()
            val estado = itemsEstado.text.isNotEmpty()
            val municipio = itemsMunicipio.text.isNotEmpty()
            val ciudad = itemsCiudad.text.isNotEmpty()

            if(!fragment.verificaciones.cadena(calle)){
                res = false
                etqCalle.error = "La calle es obligatoria"
            }else{
                etqCalle.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(colonia)){
                res = false
                etqColonia.error = "La colonia es obligatoria"
            }else{
                etqColonia.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(numero)){
                res = false
                etqNumero.error = "El número es obligatorio"
            }else{
                etqNumero.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(codigoPostal)){
                res = false
                etqCodigoPostal.error = "El ccp es obligatorio"
            }else if(codigoPostal.length != 5){
                res = false
                etqCodigoPostal.error = "El ccp debe tener 5 dígitos"
            }else{
                etqCodigoPostal.isErrorEnabled = false
            }

            if(!estado){
                res = false
                comboEstado.error = "Debe seleccionar un estado"
            }else{
                comboEstado.isErrorEnabled = false
            }

            if(!municipio){
                res = false
                comboMunicipio.error = "Debe seleccionar un municipio"
            }else{
                comboMunicipio.isErrorEnabled = false
            }

            if(!ciudad){
                res = false
                comboCiudad.error = "Debe seleccionar una ciudad"
            }else{
                comboCiudad.isErrorEnabled = false
            }

            return res
        }
    }

    private fun irCrearCuenta(){
        fragment.binding.apply{
            if(validarCampos()){
                fragment.viewModel.setDireccion(Direccion(
                    calle = txtCalle.text.toString(),
                    numero = txtNumero.text.toString(),
                    codigoPostal = txtCodigoPostal.text.toString(),
                    colonia = txtColonia.text.toString(),
                    idCiudad = fragment.idCiudad
                ))
            }
        }
    }
}