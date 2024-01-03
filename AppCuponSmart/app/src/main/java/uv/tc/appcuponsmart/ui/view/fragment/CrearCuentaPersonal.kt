package uv.tc.appcuponsmart.ui.view.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uv.tc.appcuponsmart.R
import uv.tc.appcuponsmart.controller.fragment.CrearCuentaPersonalEvent
import uv.tc.appcuponsmart.core.MensajeHelper
import uv.tc.appcuponsmart.databinding.FragmentCrearCuentaPersonalBinding
import uv.tc.appcuponsmart.di.Verificaciones
import uv.tc.appcuponsmart.ui.viewmodel.view.CrearCuentaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CrearCuentaPersonal: Fragment(){
    private var _binding: FragmentCrearCuentaPersonalBinding? = null
    val binding get() = _binding!!

    val viewModel: CrearCuentaViewModel by activityViewModels()

    private lateinit var event: CrearCuentaPersonalEvent

    @Inject
    lateinit var mensaje: MensajeHelper
    @Inject
    lateinit var verificaciones: Verificaciones
    @Inject
    lateinit var json: Gson

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentCrearCuentaPersonalBinding.inflate(inflater, container, false)

        event = CrearCuentaPersonalEvent(this)

        binding.apply{
            btnCambiarFoto.setOnClickListener(event)
            txtFechaNacimiento.setOnClickListener(event)
            btnContinuar.setOnClickListener(event)

            txtFechaNacimiento.inputType = InputType.TYPE_NULL

            viewModel.cliente.observe(viewLifecycleOwner){
                if(!verificaciones.isClassNull(savedInstanceState))
                    parentFragmentManager.commit{
                        setReorderingAllowed(true)

                        replace<CrearCuentaDomicilio>(R.id.contenedorFragment)
                    }
            }

            return root
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }

    companion object{
        @JvmStatic
        fun newInstance() = CrearCuentaPersonal()
    }
}