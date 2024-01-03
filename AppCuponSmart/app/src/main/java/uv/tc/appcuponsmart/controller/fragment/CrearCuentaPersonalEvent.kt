package uv.tc.appcuponsmart.controller.fragment

import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaPersonal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CrearCuentaPersonalEvent(
    private val fragment: CrearCuentaPersonal
): View.OnClickListener{
    private var fotoByteArray = byteArrayOf()

    override fun onClick(v: View?){
        fragment.binding.apply{
            when(v?.id){
                btnCambiarFoto.id -> cambiarFoto()
                txtFechaNacimiento.id -> seleccionarFecha()
                btnContinuar.id -> irCrearCuentaDomicilio()
            }
        }
    }

    private val pickMedia = fragment.registerForActivityResult(PickVisualMedia()){
        fragment.apply{
            it?.let{ uri ->
                fotoByteArray = activity?.contentResolver?.openInputStream(uri)?.use{ inputStream ->
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

            fragment.binding.txtFechaNacimiento.setText(
                formato.format(Date(it))
            )
        }

        datePicker.show(fragment.parentFragmentManager, "DatePicker")
    }

    private fun validarCampos(): Boolean{
        fragment.binding.apply{
            var res = true

            val nombre = txtNombre.text.toString()
            val apellidoPaterno = txtApellidoPaterno.text.toString()
            val apellidoMaterno = txtApellidoMaterno.text.toString()
            val telefono = txtTelefono.text.toString()
            val fechaNacimiento = txtFechaNacimiento.text.toString()

            if(!fragment.verificaciones.cadena(nombre)){
                res = false
                etqNombre.error = "El nombre es obligatorio"
            }else{
                etqNombre.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(apellidoPaterno)){
                res = false
                etqApellidoPaterno.error = "El apellido paterno es obligatorio"
            }else{
                etqApellidoPaterno.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(apellidoMaterno)){
                res = false
                etqApellidoMaterno.error = "El apellido materno es obligatorio"
            }else{
                etqApellidoMaterno.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(telefono)){
                res = false
                etqTelefono.error = "El teléfono es obligatorio"
            }else if(telefono.length != 10){
                res = false
                etqTelefono.error = "El télefono debe tener 10 dígitos"
            }else{
                etqTelefono.isErrorEnabled = false
            }

            if(!fragment.verificaciones.cadena(fechaNacimiento)){
                res = false
                etqFechaNacimiento.error = "La fecha de nacimiento es obligatoria"
            }else{
                etqFechaNacimiento.isErrorEnabled = false
            }

            return res
        }
    }

    private fun irCrearCuentaDomicilio(){
        fragment.binding.apply{
            if(validarCampos()){
                fragment.viewModel.setCliente(Cliente(
                    nombre = txtNombre.text.toString(),
                    apellidoPaterno = txtApellidoPaterno.text.toString(),
                    apellidoMaterno = txtApellidoMaterno.text.toString(),
                    telefono = txtTelefono.text.toString(),
                    fechaNacimiento = txtFechaNacimiento.text.toString(),
                    fotoBase64 = fragment.verificaciones.byteArrayToBase64(fotoByteArray)
                ))
            }
        }
    }
}