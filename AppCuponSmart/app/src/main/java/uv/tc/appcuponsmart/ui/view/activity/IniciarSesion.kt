package uv.tc.appcuponsmart.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.controller.activity.IniciarSesionEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.databinding.ActivityIniciarSesionBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.AutenticacionViewModel
import javax.inject.Inject

@AndroidEntryPoint
class IniciarSesion: AppCompatActivity(){
    private lateinit var binding: ActivityIniciarSesionBinding

    private val autenticacion: AutenticacionViewModel by viewModels()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "almacenamiento")

    private lateinit var event: IniciarSesionEvent

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        event = IniciarSesionEvent(this, binding, autenticacion)

        binding.btnIniciarSesion.setOnClickListener(event)
        binding.crearCuenta.setOnClickListener(event)

        autenticacion.error.observe(this){
            it?.let{ error ->
                mostrarMensaje(Constantes.Mensajes.ERROR, error)
            }
        }

        autenticacion.cliente.observe(this){ cliente ->
            if(cliente != null){
                cliente.id?.let{
                    lifecycleScope.launch(Dispatchers.IO){
                        dataStore.edit{ preferences ->
                            preferences[intPreferencesKey("idCliente")] = it
                        }
                    }

                    mostrarMensaje(Constantes.Mensajes.EXITO, Constantes.Mensajes.bienvenida(cliente.nombre.toString()))

                    startActivity(Intent(this, Home::class.java)
                        .putExtra("idCliente", it)
                    )
                    finish()
                }
            }else mensaje.mostrarMensaje(supportFragmentManager, Constantes.Mensajes.ADVERTENCIA, "Las credenciales son incorrectas, favor de verificarlas")
        }
    }

    private fun mostrarMensaje(titulo: String, contenido: String) =
        mensaje.mostrarMensaje(supportFragmentManager, titulo, contenido)
}