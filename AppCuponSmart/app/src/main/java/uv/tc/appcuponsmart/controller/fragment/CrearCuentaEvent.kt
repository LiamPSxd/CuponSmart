package uv.tc.appcuponsmart.controller.fragment

import android.view.View
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuenta

class CrearCuentaEvent(
    private val fragment: CrearCuenta
): View.OnClickListener{
    override fun onClick(v: View?){
        when(v?.id){
            fragment.binding.btnCrearCuenta.id -> crearCuenta()
        }
    }

    private fun validarCampos(): Boolean{
        fragment.binding.apply{
            var res = true

            val correo = txtCorreo.text.toString()
            val contrasenia = txtContrasenia.text.toString()
            val confirmarContrasenia = txtConfirmarContrasenia.text.toString()

            if(!fragment.verificaciones.cadena(correo)){
                res = false
                etqCorreo.error = "El correo es obligatorio"
            }else{
                etqCorreo.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(contrasenia)){
                res = false
                etqContrasenia.error = "La contraseña es obligatoria"
            }else{
                etqContrasenia.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(confirmarContrasenia)){
                res = false
                etqConfirmarContrasenia.error = "Debe confirmar la contraseña"
            }else{
                etqConfirmarContrasenia.isErrorEnabled = false
            }

            if(!fragment.verificaciones.validarCorreo(correo)){
                res = false
                fragment.mostrarMensaje(Constantes.Mensajes.ADVERTENCIA, "El correo no es válido")
            }

            if(contrasenia != confirmarContrasenia){
                res = false
                fragment.mostrarMensaje(Constantes.Mensajes.ADVERTENCIA, "Las contraseñas no coinciden")
            }

            return res
        }
    }

    private fun crearCuenta(){
        if(validarCampos())
            fragment.viewModel.getIdCliente(
                fragment.binding.txtCorreo.text.toString()
            )
    }
}