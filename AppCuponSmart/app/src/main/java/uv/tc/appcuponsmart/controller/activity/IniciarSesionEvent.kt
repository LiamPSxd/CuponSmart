package uv.tc.appcuponsmart.controller.activity

import android.content.Intent
import android.view.View
import uv.tc.appcuponsmart.databinding.ActivityIniciarSesionBinding
import uv.tc.appcuponsmart.ui.view.activity.CrearCuenta
import uv.tc.appcuponsmart.ui.view.activity.IniciarSesion
import uv.tc.appcuponsmart.ui.viewmodel.AutenticacionViewModel

class IniciarSesionEvent(
    private val activity: IniciarSesion,
    private val binding: ActivityIniciarSesionBinding,
    private val autenticacion: AutenticacionViewModel
): View.OnClickListener{
    override fun onClick(v: View?){
        when(v?.id){
            binding.btnIniciarSesion.id -> iniciarSesion()
            binding.crearCuenta.id -> irCrearCuenta()
        }
    }

    private fun validarCampos(correo: String, contrasenia: String): Boolean{
        var res = true

        if(!activity.verificaciones.cadena(correo)){
            res = false
            binding.etqCorreo.error = "El correo es obligatorio"
        }

        if(!activity.verificaciones.cadena(contrasenia)){
            res = false
            binding.etqContrasenia.error = "La contraseña es obligatoria"
        }

        return res
    }

    private fun iniciarSesion(){
        val correo = binding.txtCorreo.text.toString()
        val contrasenia = binding.txtContrasenia.text.toString()

        if(validarCampos(correo, contrasenia))
            autenticacion.loginMovil(correo, contrasenia)
    }

    private fun irCrearCuenta() =
        activity.startActivity(Intent(activity, CrearCuenta::class.java))
}