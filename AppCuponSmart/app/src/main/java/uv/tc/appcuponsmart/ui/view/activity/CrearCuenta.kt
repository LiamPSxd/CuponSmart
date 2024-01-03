package uv.tc.appcuponsmart.ui.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.databinding.ActivityCrearCuentaBinding
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaPersonal
import uv.tc.appcuponsmart.ui.viewmodel.view.CrearCuentaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CrearCuenta: AppCompatActivity(){
    private lateinit var binding: ActivityCrearCuentaBinding

    private val viewModel: CrearCuentaViewModel by viewModels()

    @Inject
    lateinit var verificaciones: Verificaciones

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater).apply{
            setContentView(root)

            if(savedInstanceState == null)
                supportFragmentManager.commit{
                    setReorderingAllowed(true)

                    add<CrearCuentaPersonal>(contenedorFragment.id)
                }
        }
    }
}