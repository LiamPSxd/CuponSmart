package uv.tc.appcuponsmart.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import uv.tc.appcuponsmart.databinding.DialogMensajeCancelarBinding

class MensajeCerrarSesion(
    private val titulo: String? = "Sin título",
    private val contenido: String? = "Sin contenido"
): DialogFragment(){
    private var _binding: DialogMensajeCancelarBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: MensajeCerrarSesionListener

    interface MensajeCerrarSesionListener{
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)

        try{
            listener = context as MensajeCerrarSesionListener
        }catch(_: ClassCastException){}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        _binding = DialogMensajeCancelarBinding.inflate(layoutInflater)

        onAttach(requireContext())

        binding.apply{
            txtTitulo.text = titulo
            txtContenido.text = contenido

            btnAceptar.setOnClickListener{
                listener.onDialogPositiveClick(this@MensajeCerrarSesion)
            }

            binding.btnCancelar.setOnClickListener{
                dismiss()
            }
        }

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}