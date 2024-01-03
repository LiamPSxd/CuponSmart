package uv.tc.appcuponsmart.controller.activity

import android.content.Intent
import android.view.View
import uv.tc.appcuponsmart.ui.view.activity.CrearCuenta
import uv.tc.appcuponsmart.ui.view.activity.IniciarSesion

class IniciarSesionEvent(
    private val activity: IniciarSesion
): View.OnClickListener{
    override fun onClick(v: View?){
        activity.binding.apply{
            when(v?.id){
                btnIniciarSesion.id -> iniciarSesion()
                crearCuenta.id -> irCrearCuenta()
            }
        }
    }

    private fun validarCampos(correo: String, contrasenia: String): Boolean{
        activity.apply{
            var res = true

            if(!verificaciones.cadena(correo)){
                res = false
                binding.etqCorreo.error = "El correo es obligatorio"
            }else{
                binding.etqCorreo.isErrorEnabled = false
            }

            if(!verificaciones.cadena(contrasenia)){
                res = false
                binding.etqContrasenia.error = "La contraseña es obligatoria"
            }else{
                binding.etqContrasenia.isErrorEnabled = false
            }

            return res
        }
    }

    private fun iniciarSesion(){
        activity.binding.apply{
            val correo = txtCorreo.text.toString()
            val contrasenia = txtContrasenia.text.toString()

            if(validarCampos(correo, contrasenia)){
                activity.viewModel.loginMovil(correo, contrasenia)
            }
        }
    }

    private fun irCrearCuenta() =
        activity.startActivity(Intent(activity, CrearCuenta::class.java))
}