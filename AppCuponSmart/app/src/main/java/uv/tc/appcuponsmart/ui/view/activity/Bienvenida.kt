package uv.tc.appcuponsmart.ui.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.databinding.ActivityBienvenidaBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.ClienteViewModel
import javax.inject.Inject

@AndroidEntryPoint
class Bienvenida: AppCompatActivity(){
    private lateinit var binding: ActivityBienvenidaBinding

    private val cliente: ClienteViewModel by viewModels()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "almacenamiento")

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cliente.error.observe(this){
            it?.let{ error ->
                mensaje.mostrarMensaje(supportFragmentManager, Constantes.Mensajes.ERROR, error)
            }
        }

        cliente.status.observe(this){
            binding.carga.isVisible = it
        }

        Handler(Looper.getMainLooper()).postDelayed({
            var idCliente = 0
            dataStore.data.map{ preferences ->
                idCliente = preferences[intPreferencesKey("idCliente")] ?: 0
            }

            if(verificaciones.numerico(idCliente))
                startActivity(Intent(this, Home::class.java)
                    .putExtra("idCliente", idCliente)
                )
            else
                startActivity(Intent(this, IniciarSesion::class.java))

            finish()
        }, 3000)
    }
}