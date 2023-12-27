package uv.tc.appcuponsmart.controller.fragment

import android.view.View
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuenta

class CrearCuentaEvent(
    private val fragment: CrearCuenta,
    private val binding: FragmentCrearCuentaBinding
): View.OnClickListener{
    override fun onClick(v: View?){
        when(v?.id){
            binding.btnCrearCuenta.id -> crearCuenta()
        }
    }

    private fun validarCampos(): Boolean{
        var res = true

        fragment.cliente.getClientes()

        val correo = binding.txtCorreo.text.toString()
        val contrasenia = binding.txtContrasenia.text.toString()
        val confirmarContrasenia = binding.txtConfirmarContrasenia.text.toString()

        if(!fragment.verificaciones.cadena(correo)){
            res = false
            binding.etqCorreo.error = "El correo es obligatorio"
        }

        if(!fragment.verificaciones.validarCorreo(correo)){
            res = false
            fragment.mostrarMensaje(Constantes.Mensajes.ADVERTENCIA, "El correo no es válido")
        }

        if(!fragment.verificaciones.validarCorreoNoRegistrado(correo, fragment.correos)){
            res = false
            fragment.mostrarMensaje(Constantes.Mensajes.ERROR, "El correo ya está registrado")
        }

        if(!fragment.verificaciones.cadena(contrasenia)){
            res = false
            binding.etqContrasenia.error = "La contraseña es obligatoria"
        }

        if(!fragment.verificaciones.cadena(confirmarContrasenia)){
            res = false
            binding.etqConfirmarContrasenia.error = "Debe confirmar la contraseña"
        }

        if(contrasenia != confirmarContrasenia){
            res = false
            fragment.mostrarMensaje(Constantes.Mensajes.ADVERTENCIA, "Las contraseñas no coinciden")
        }

        return res
    }

    private fun crearCuenta(){
        if(validarCampos() && !fragment.verificaciones.isClassNull(fragment.cli) && !fragment.verificaciones.isClassNull(fragment.direc)){
            fragment.cli?.correo = binding.txtCorreo.text.toString()
            fragment.cli?.contrasenia = binding.txtContrasenia.text.toString()

            fragment.direccion.addDireccion(fragment.direc!!)

            if(fragment.verificaciones.numerico(fragment.direc?.id!!)){
                fragment.cliente.addCliente(fragment.cli!!)
            }
        }
    }
}