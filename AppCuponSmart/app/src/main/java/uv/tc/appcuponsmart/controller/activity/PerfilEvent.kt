package uv.tc.appcuponsmart.controller.activity

import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.activity.Perfil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class PerfilEvent(
    private val activity: Perfil
): View.OnClickListener{
    override fun onClick(v: View?){
        activity.binding.apply{
            when(v?.id){
                btnAceptar.id -> modificarCliente()
                btnCancelar.id -> regresarEstado()
                btnModificar.id -> habilitarComponentes(true)
                btnCerrarSesion.id -> cerrarSesion()
                btnCambiarFoto.id -> cambiarFoto()
                txtFechaNacimiento.id -> seleccionarFecha()
            }
        }
    }

    private fun validarCampos(): Boolean{
        activity.binding.apply{
            var res = true

            val nombre = txtNombre.text.toString()
            val apellidoPaterno = txtApellidoPaterno.text.toString()
            val apellidoMaterno = txtApellidoMaterno.text.toString()
            val telefono = txtTelefono.text.toString()
            val fechaNacimiento = txtFechaNacimiento.text.toString()

            val calle = txtCalle.text.toString()
            val colonia = txtColonia.text.toString()
            val numero = txtNumero.text.toString()
            val codigoPostal = txtCodigoPostal.text.toString()
            val estado = itemsEstado.text.isNotEmpty()
            val municipio = itemsMunicipio.text.isNotEmpty()
            val ciudad = itemsCiudad.text.isNotEmpty()

            val contrasenia = txtContrasenia.text.toString()
            val confirmarContrasenia = txtConfirmarContrasenia.text.toString()

            if(!activity.verificaciones.cadena(nombre)){
                res = false
                etqNombre.error = "El nombre es obligatorio"
            }else{
                etqNombre.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(apellidoPaterno)){
                res = false
                etqApellidoPaterno.error = "El apellido paterno es obligatorio"
            }else{
                etqApellidoPaterno.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(apellidoMaterno)){
                res = false
                etqApellidoMaterno.error = "El apellido materno es obligatorio"
            }else{
                etqApellidoMaterno.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(telefono)){
                res = false
                etqTelefono.error = "El teléfono es obligatorio"
            }else if(telefono.length != 10){
                res = false
                etqTelefono.error = "El teléfono debe tener 10 dígitos"
            }else{
                etqTelefono.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(fechaNacimiento)){
                res = false
                etqFechaNacimiento.error = "La fecha de nacimiento es obligatoria"
            }else{
                etqFechaNacimiento.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(calle)){
                res = false
                etqCalle.error = "La calle es obligatoria"
            }else{
                etqCalle.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(colonia)){
                res = false
                etqColonia.error = "La colonia es obligatoria"
            }else{
                etqColonia.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(numero)){
                res = false
                etqNumero.error = "El número es obligatorio"
            }else{
                etqNumero.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(codigoPostal)){
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

            if(!activity.verificaciones.cadena(contrasenia)){
                res = false
                etqContrasenia.error = "La contraseña es obligatoria"
            }else{
                etqContrasenia.isErrorEnabled = false
            }

            if(!activity.verificaciones.cadena(confirmarContrasenia)){
                res = false
                etqConfirmarContrasenia.error = "Debe confirmar la contraseña"
            }else{
                etqConfirmarContrasenia.isErrorEnabled = false
            }

            if(contrasenia != confirmarContrasenia){
                res = false
                activity.mostrarMensaje(Constantes.Mensajes.ADVERTENCIA, "Las contraseñas no coinciden")
            }

            return res
        }
    }

    private fun modificarCliente(){
        activity.binding.apply{
            if(validarCampos()){
                activity.viewModel.cliente.value?.let{ cliente ->
                    cliente.nombre = txtNombre.text.toString()
                    cliente.apellidoPaterno = txtApellidoPaterno.text.toString()
                    cliente.apellidoMaterno = txtApellidoMaterno.text.toString()
                    cliente.telefono = txtTelefono.text.toString()
                    cliente.fechaNacimiento = txtFechaNacimiento.text.toString()
                    cliente.contrasenia = txtContrasenia.text.toString()
                }

                activity.viewModel.direccion.value?.let{ direccion ->
                    direccion.calle = txtCalle.text.toString()
                    direccion.numero = txtNumero.text.toString()
                    direccion.codigoPostal = txtCodigoPostal.text.toString()
                    direccion.colonia = txtColonia.text.toString()
                    direccion.idCiudad = activity.ciudadItem.id

                    activity.viewModel.updateDireccion(direccion)
                }
            }
        }
    }

    private fun regresarEstado(){
        activity.binding.apply{
            activity.recuperarIdCliente()

            habilitarComponentes(false)
        }
    }

    fun habilitarComponentes(editable: Boolean){
        activity.binding.apply{
            if(editable){
                btnModificar.visibility = View.GONE
                btnCerrarSesion.visibility = View.GONE
                btnAceptar.visibility = View.VISIBLE
                btnCancelar.visibility = View.VISIBLE
                btnCambiarFoto.visibility = View.VISIBLE
                etqConfirmarContrasenia.visibility = View.VISIBLE
            }else{
                btnModificar.visibility = View.VISIBLE
                btnCerrarSesion.visibility = View.VISIBLE
                btnAceptar.visibility = View.GONE
                btnCancelar.visibility = View.GONE
                btnCambiarFoto.visibility = View.INVISIBLE
                etqConfirmarContrasenia.visibility = View.GONE
            }

            etqNombre.isEnabled = editable
            etqApellidoPaterno.isEnabled = editable
            etqApellidoMaterno.isEnabled = editable
            etqTelefono.isEnabled = editable
            etqFechaNacimiento.isEnabled = editable

            etqCalle.isEnabled = editable
            etqColonia.isEnabled = editable
            etqNumero.isEnabled = editable
            etqCodigoPostal.isEnabled = editable
            comboEstado.isEnabled = editable
            comboMunicipio.isEnabled = editable
            comboCiudad.isEnabled = editable

            etqContrasenia.isEnabled = editable
            etqConfirmarContrasenia.isEnabled = editable

            etqNombre.isErrorEnabled = false
            etqApellidoPaterno.isErrorEnabled = false
            etqApellidoMaterno.isErrorEnabled = false
            etqTelefono.isErrorEnabled = false
            etqFechaNacimiento.isErrorEnabled = false

            etqCalle.isErrorEnabled = false
            etqColonia.isErrorEnabled = false
            etqNumero.isErrorEnabled = false
            etqCodigoPostal.isErrorEnabled = false
            comboEstado.isErrorEnabled = false
            comboMunicipio.isErrorEnabled = false
            comboCiudad.isErrorEnabled = false

            etqContrasenia.isErrorEnabled = false
            etqConfirmarContrasenia.isErrorEnabled = false
            etqConfirmarContrasenia.isErrorEnabled = false

            txtConfirmarContrasenia.setText("")
        }
    }

    private fun cerrarSesion() =
        activity.mostrarMensajeCerrarSesion(Constantes.Mensajes.ADVERTENCIA, "¿Desea cerrar su sesión?")

    private val pickMedia = activity.registerForActivityResult(PickVisualMedia()){
        activity.apply{
            it?.let{ uri ->
                fotoByteArray = contentResolver.openInputStream(uri)?.use{ inputStream ->
                    inputStream.buffered().readBytes()
                } ?: byteArrayOf()

                binding.foto.setImageURI(uri)
            }
        }
    }

    private fun cambiarFoto() =
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))

    private fun seleccionarFecha(){
        val formato = SimpleDateFormat(Constantes.Utileria.FORMATO_FECHA, Locale.getDefault())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(Constantes.Utileria.TITULO_PICKER)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTextInputFormat(formato)
            .setCalendarConstraints(
                CalendarConstraints.Builder().setEnd(MaterialDatePicker.todayInUtcMilliseconds()).build()
            )
            .build()

        datePicker.addOnPositiveButtonClickListener{
            formato.timeZone = TimeZone.getTimeZone(Constantes.Utileria.TIME_ZONE)

            activity.binding.txtFechaNacimiento.setText(
                formato.format(Date(it))
            )
        }

        datePicker.show(activity.supportFragmentManager, "DatePicker")
    }
}