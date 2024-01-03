package uv.tc.appcuponsmart.ui.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.controller.activity.IniciarSesionEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.databinding.ActivityIniciarSesionBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.view.IniciarSesionViewModel
import javax.inject.Inject

@AndroidEntryPoint
class IniciarSesion: AppCompatActivity(){
    lateinit var binding: ActivityIniciarSesionBinding

    val viewModel: IniciarSesionViewModel by viewModels()

    private lateinit var event: IniciarSesionEvent

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater).apply{
            setContentView(root)

            event = IniciarSesionEvent(this@IniciarSesion)

            btnIniciarSesion.setOnClickListener(event)
            crearCuenta.setOnClickListener(event)
        }

        viewModel.error.observe(this){ error ->
            error?.let{ err ->
                mostrarMensaje(Constantes.Mensajes.ERROR, err)
            }
        }

        viewModel.cliente.observe(this){ cliente ->
            if(cliente != null){
                cliente.id?.let{ id ->
                    viewModel.putIdCliente(id)
                    viewModel.putNombreCliente("${cliente.nombre}, 1")

                    startActivity(Intent(this, Home::class.java)
                        .putExtra(Constantes.DataStore.ID_CLIENTE, id)
                    )
                    finish()
                }
            }else mostrarMensaje(Constantes.Mensajes.ADVERTENCIA, "Las credenciales son incorrectas, favor de verificarlas")
        }

        buscarCorreo()
    }

    private fun buscarCorreo(){
        val correo = intent.extras?.getString(Constantes.DataStore.CORREO_CLIENTE) ?: ""

        if(verificaciones.cadena(correo)){
            binding.txtCorreo.setText(correo)

            mostrarMensaje(Constantes.Mensajes.EXITO, "Su cuenta se ha creado exitosamente, ahora ingrese su contraseña para confirmarla")
        }
    }

    private fun mostrarMensaje(titulo: String, contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, titulo, contenido)
}