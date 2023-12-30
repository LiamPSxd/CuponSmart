package uv.tc.appcuponsmart.ui.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.databinding.ActivityCrearCuentaBinding
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaPersonal
import uv.tc.appcuponsmart.ui.viewmodel.view.CrearCuentaViewModel

@AndroidEntryPoint
class CrearCuenta: AppCompatActivity(){
    private lateinit var binding: ActivityCrearCuentaBinding

    private val viewModel: CrearCuentaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater).apply{
            setContentView(root)

            supportFragmentManager.commit{
                setReorderingAllowed(true)
                add<CrearCuentaPersonal>(contenedorFragment.id)
            }
        }
    }
}