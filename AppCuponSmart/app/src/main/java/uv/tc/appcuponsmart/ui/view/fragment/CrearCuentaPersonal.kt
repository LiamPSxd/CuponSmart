package uv.tc.appcuponsmart.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.controller.fragment.CrearCuentaPersonalEvent
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaPersonalBinding
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

@AndroidEntryPoint
class CrearCuentaPersonal: Fragment(){
    private var _binding: FragmentCrearCuentaPersonalBinding? = null
    private val binding get() = _binding!!

    private lateinit var event: CrearCuentaPersonalEvent

    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        event = CrearCuentaPersonalEvent(this, binding, savedInstanceState)

        binding.btnCambiarFoto.setOnClickListener(event)
        binding.etqFechaNacimiento.setOnClickListener(event)
        binding.btnContinuar.setOnClickListener(event)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentCrearCuentaPersonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}