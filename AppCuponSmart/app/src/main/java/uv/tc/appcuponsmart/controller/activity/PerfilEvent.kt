package uv.tc.appcuponsmart.controller.activity

import android.view.View
import android.widget.AdapterView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.net.toFile
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.android.material.datepicker.MaterialDatePicker
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.databinding.ActivityPerfilBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.ui.view.activity.Perfil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PerfilEvent(
    private val activity: Perfil,
    private val binding: ActivityPerfilBinding,
    private val dataStore: DataStore<Preferences>
): View.OnClickListener, AdapterView.OnItemSelectedListener{
    private var isEditable = false
    private var fotoByteArray = byteArrayOf()

    override fun onClick(v: View?){
        when(v?.id){
            binding.btnAceptar.id -> modificarCliente()
            binding.btnCancelar.id -> regresarEstado()
            binding.btnModificar.id -> habilitarComponentes()
            binding.btnCerrarSesion.id -> cerrarSesion()
            binding.btnCambiarFoto.id -> cambiarFoto()
            binding.etqFechaNacimiento.id -> seleccionarFecha()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
        val item = parent?.getItemAtPosition(position)

        when(view?.id){
            binding.itemsEstado.id -> obtenerMunicipios((item as Estado).id!!)
            binding.itemsMunicipio.id -> obtenerCiudades((item as Municipio).id!!)
            binding.itemsCiudad.id -> activity.idCiudad = (item as Ciudad).id!!
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?){}

    private fun validarCampos(): Boolean{
        var res = true

        val nombre = binding.txtNombre.text.toString()
        val apellidoPaterno = binding.txtApellidoPaterno.text.toString()
        val apellidoMaterno = binding.txtApellidoMaterno.text.toString()
        val telefono = binding.txtTelefono.text.toString()
        val fechaNacimiento = binding.txtFechaNacimiento.text.toString()

        val calle = binding.txtCalle.text.toString()
        val colonia = binding.txtColonia.text.toString()
        val numero = binding.txtNumero.text.toString()
        val codigoPostal = binding.txtCodigoPostal.text.toString()
        val estado = binding.itemsEstado.isSelected
        val municipio = binding.itemsMunicipio.isSelected
        val ciudad = binding.itemsCiudad.isSelected

        val contrasenia = binding.txtContrasenia.text.toString()
        val confirmarContrasenia = binding.txtConfirmarContrasenia.text.toString()

        if(!activity.verificaciones.cadena(nombre)){
            res = false
            binding.etqNombre.error = "El nombre es obligatorio"
        }

        if(!activity.verificaciones.cadena(apellidoPaterno)){
            res = false
            binding.etqApellidoPaterno.error = "El apellido paterno es obligatorio"
        }

        if(!activity.verificaciones.cadena(apellidoMaterno)){
            res = false
            binding.etqApellidoMaterno.error = "El apellido materno es obligatorio"
        }

        if(!activity.verificaciones.cadena(telefono)){
            res = false
            binding.etqTelefono.error = "El teléfono es obligatorio"
        }else if(telefono.length != 10){
            res = false
            binding.etqTelefono.error = "El teléfono debe tener 10 dígitos"
        }

        if(!activity.verificaciones.cadena(fechaNacimiento)){
            res = false
            binding.etqFechaNacimiento.error = "La fecha de nacimiento es obligatoria"
        }

        if(!activity.verificaciones.cadena(calle)){
            res = false
            binding.etqCalle.error = "La calle es obligatoria"
        }

        if(!activity.verificaciones.cadena(colonia)){
            res = false
            binding.etqColonia.error = "La colonia es obligatoria"
        }

        if(!activity.verificaciones.cadena(numero)){
            res = false
            binding.etqNumero.error = "El número es obligatorio"
        }

        if(!activity.verificaciones.cadena(codigoPostal)){
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

        if(!activity.verificaciones.cadena(contrasenia)){
            res = false
            binding.etqContrasenia.error = "La contraseña es obligatoria"
        }

        if(!activity.verificaciones.cadena(confirmarContrasenia)){
            res = false
            binding.etqConfirmarContrasenia.error = "Debe confirmar la contraseña"
        }

        if(contrasenia != confirmarContrasenia){
            res = false
            activity.mostrarMensaje(Constantes.Mensajes.ADVERTENCIA, "Las contraseñas no coinciden")
        }

        return res
    }

    private fun modificarCliente(){
        activity.cliente.cliente.value?.let{
            it.nombre = binding.txtNombre.text.toString()
            it.apellidoPaterno = binding.txtApellidoPaterno.text.toString()
            it.apellidoMaterno = binding.txtApellidoMaterno.text.toString()
            it.telefono = binding.txtTelefono.text.toString()
            it.fechaNacimiento = binding.txtFechaNacimiento.text.toString()
            it.contrasenia = binding.txtContrasenia.text.toString()
        }

        activity.direccion.direccion.value?.let{
            it.calle = binding.txtCalle.text.toString()
            it.numero = binding.txtNumero.text.toString()
            it.codigoPostal = binding.txtCodigoPostal.text.toString()
            it.colonia = binding.txtColonia.text.toString()
            it.idCiudad = activity.idCiudad
        }

        if(validarCampos())
            activity.direccion.direccion.value?.let{
                activity.direccion.updateDireccion(it)

                if(activity.direccion.status.value == true){
                    activity.media.addFotoCliente(activity.cliente.cliente.value?.id!!, fotoByteArray)

                    habilitarComponentes()

                    binding.btnModificar.visibility = View.VISIBLE
                    binding.btnCerrarSesion.visibility = View.VISIBLE
                    binding.btnAceptar.visibility = View.GONE
                    binding.btnCancelar.visibility = View.GONE
                    binding.btnCambiarFoto.visibility = View.INVISIBLE
                    binding.etqConfirmarContrasenia.visibility = View.INVISIBLE
                }
            }
    }

    private fun regresarEstado(){
        activity.recuperarIdCliente()

        habilitarComponentes()

        binding.btnModificar.visibility = View.VISIBLE
        binding.btnCerrarSesion.visibility = View.VISIBLE
        binding.btnAceptar.visibility = View.GONE
        binding.btnCancelar.visibility = View.GONE
        binding.btnCambiarFoto.visibility = View.INVISIBLE
        binding.etqConfirmarContrasenia.visibility = View.INVISIBLE
    }

    private fun habilitarComponentes(){
        isEditable = !isEditable

        binding.btnModificar.visibility = View.GONE
        binding.btnCerrarSesion.visibility = View.GONE
        binding.btnAceptar.visibility = View.VISIBLE
        binding.btnCancelar.visibility = View.VISIBLE
        binding.btnCambiarFoto.visibility = View.VISIBLE

        binding.etqNombre.isEnabled = isEditable
        binding.etqApellidoPaterno.isEnabled = isEditable
        binding.etqApellidoMaterno.isEnabled = isEditable
        binding.etqTelefono.isEnabled = isEditable
        binding.etqFechaNacimiento.isEnabled = isEditable

        binding.etqCalle.isEnabled = isEditable
        binding.etqColonia.isEnabled = isEditable
        binding.etqNumero.isEnabled = isEditable
        binding.etqCodigoPostal.isEnabled = isEditable
        binding.comboEstado.isEnabled = isEditable
        binding.comboMunicipio.isEnabled = isEditable
        binding.comboCiudad.isEnabled = isEditable

        binding.etqContrasenia.isEnabled = isEditable
        binding.etqConfirmarContrasenia.isEnabled = isEditable

        binding.etqConfirmarContrasenia.visibility = View.VISIBLE
    }

    private fun cerrarSesion() =
        activity.mensaje.mostrarMensajeCerrarSesion(activity, dataStore, Constantes.Mensajes.ADVERTENCIA, "¿Desea cerrar su sesión?")

    private val pickMedia = activity.registerForActivityResult(PickVisualMedia()){
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

        datePicker.show(activity.supportFragmentManager, "DatePicker")
    }

    private fun obtenerMunicipios(idEstado: Int) =
        activity.catalogo.getMunicipiosPorEstado(idEstado)

    private fun obtenerCiudades(idMunicipio: Int) =
        activity.ciudad.getCiudadesPorMunicipio(idMunicipio)
}