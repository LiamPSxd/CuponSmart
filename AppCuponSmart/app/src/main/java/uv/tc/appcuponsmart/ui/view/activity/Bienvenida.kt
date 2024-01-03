package uv.tc.appcuponsmart.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.databinding.ActivityBienvenidaBinding
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.view.BienvenidaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class Bienvenida: AppCompatActivity(){
    private lateinit var binding: ActivityBienvenidaBinding

    private val viewModel: BienvenidaViewModel by viewModels()

    @Inject
    lateinit var verificaciones: Verificaciones

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityBienvenidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.idCliente.observe(this){ idCliente ->
                if(verificaciones.numerico(idCliente))
                    startActivity(Intent(this, Home::class.java)
                        .putExtra(Constantes.DataStore.ID_CLIENTE, idCliente)
                    )
                else
                    startActivity(Intent(this, IniciarSesion::class.java))

                finish()
            }

            viewModel.getIdCliente()
        }, 3000)
    }
}