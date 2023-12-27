package uv.tc.appcuponsmart.controller.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.net.toFile
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.datepicker.MaterialDatePicker
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaPersonalBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaDomicilio
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaPersonal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CrearCuentaPersonalEvent(
    private val fragment: CrearCuentaPersonal,
    private val binding: FragmentCrearCuentaPersonalBinding,
    private val savedInstanceState: Bundle?
): View.OnClickListener{
    private var fotoByteArray = byteArrayOf()

    override fun onClick(v: View?){
        when(v?.id){
            binding.btnCambiarFoto.id -> cambiarFoto()
            binding.etqFechaNacimiento.id -> seleccionarFecha()
            binding.btnContinuar.id -> irCrearCuentaDomicilio()
        }
    }

    private val pickMedia = fragment.registerForActivityResult(PickVisualMedia()){
        it?.let{ uri ->
            fotoByteArray = uri.toFile().readBytes()
            binding.foto.setImageURI(uri)
        }
    }

    private fun cambiarFoto() =
        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))

    private fun seleccionarFecha(){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona una fecha")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener{
            binding.txtFechaNacimiento.setText(
                SimpleDateFormat(Constantes.Utileria.FORMATO_FECHA, Locale.US)
                    .format(Date(it))
            )
        }

        datePicker.show(fragment.parentFragmentManager, "DatePicker")
    }

    private fun validarCampos(): Boolean{
        var res = true

        val nombre = binding.txtNombre.text.toString()
        val apellidoPaterno = binding.txtApellidoPaterno.text.toString()
        val apellidoMaterno = binding.txtApellidoMaterno.text.toString()
        val telefono = binding.txtTelefono.text.toString()
        val fechaNacimiento = binding.txtFechaNacimiento.text.toString()

        if(!fragment.verificaciones.cadena(nombre)){
            res = false
            binding.etqNombre.error = "El nombre es obligatorio"
        }

        if(!fragment.verificaciones.cadena(apellidoPaterno)){
            res = false
            binding.etqApellidoPaterno.error = "El apellido paterno es obligatorio"
        }

        if(!fragment.verificaciones.cadena(apellidoMaterno)){
            res = false
            binding.etqApellidoMaterno.error = "El apellido materno es obligatorio"
        }

        if(!fragment.verificaciones.cadena(telefono)){
            res = false
            binding.etqTelefono.error = "El teléfono es obligatorio"
        }else if(telefono.length != 10){
            res = false
            binding.etqTelefono.error = "El télefono debe tener 10 dígitos"
        }

        if(!fragment.verificaciones.cadena(fechaNacimiento)){
            res = false
            binding.etqFechaNacimiento.error = "La fecha de nacimiento es obligatoria"
        }

        return res
    }

    private fun irCrearCuentaDomicilio(){
        if(validarCampos()){
            val cliente = Cliente(
                nombre = binding.txtNombre.text.toString(),
                apellidoPaterno = binding.txtApellidoPaterno.text.toString(),
                apellidoMaterno = binding.txtApellidoMaterno.text.toString(),
                telefono = binding.txtTelefono.text.toString(),
                fechaNacimiento = binding.txtFechaNacimiento.text.toString()
            )

            if(fragment.verificaciones.isClassNull(savedInstanceState))
                fragment.parentFragmentManager.commit{
                    setReorderingAllowed(true)
                    replace<CrearCuentaDomicilio>(R.id.contenedorFragment, args = bundleOf(
                        Constantes.Utileria.CLIENTE to fragment.json.toJson(cliente),
                        Constantes.Utileria.FOTO to fotoByteArray
                    ))
                }
        }
    }
}