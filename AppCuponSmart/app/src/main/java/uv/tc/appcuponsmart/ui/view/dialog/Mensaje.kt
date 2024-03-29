package uv.tc.appcuponsmart.ui.view.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import uv.tc.appcuponsmart.databinding.DialogMensajeBinding

class Mensaje(
    private val titulo: String? = "Sin título",
    private val contenido: String? = "Sin contenido"
): DialogFragment(){
    private var _binding: DialogMensajeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        _binding = DialogMensajeBinding.inflate(layoutInflater)

        binding.txtTitulo.text = titulo
        binding.txtContenido.text = contenido

        binding.btnAceptar.setOnClickListener{ dismiss() }

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}