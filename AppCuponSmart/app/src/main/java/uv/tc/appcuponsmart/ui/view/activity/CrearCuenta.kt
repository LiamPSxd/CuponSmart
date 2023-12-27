package uv.tc.appcuponsmart.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.databinding.ActivityCrearCuentaBinding
import uv.tc.appcuponsmart.ui.view.fragment.CrearCuentaPersonal

@AndroidEntryPoint
class CrearCuenta: AppCompatActivity(){
    private lateinit var binding: ActivityCrearCuentaBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit{
            setReorderingAllowed(true)
            add<CrearCuentaPersonal>(binding.contenedorFragment.id)
        }
    }
}