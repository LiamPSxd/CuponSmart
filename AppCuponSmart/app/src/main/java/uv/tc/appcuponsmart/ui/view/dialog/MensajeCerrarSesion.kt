package uv.tc.appcuponsmart.ui.view.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.databinding.DialogMensajeCancelarBinding
import uv.tc.appcuponsmart.ui.view.activity.IniciarSesion
import javax.inject.Inject

class MensajeCerrarSesion @Inject constructor(): DialogFragment(){
    private var _binding: DialogMensajeCancelarBinding? = null
    private val binding get() = _binding!!

    lateinit var activity: AppCompatActivity
    lateinit var dataStore: DataStore<Preferences>
    var titulo: String? = "Sin título"
    var contenido: String? = "Sin contenido"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        _binding = DialogMensajeCancelarBinding.inflate(layoutInflater)

        binding.txtTitulo.text = titulo
        binding.txtContenido.text = contenido

        binding.btnAceptar.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO){
                dataStore.edit{ preferences ->
                    preferences[intPreferencesKey("idCliente")] = 0
                }
            }

            activity.startActivity(Intent(activity, IniciarSesion::class.java))
            activity.finish()
        }

        binding.btnCancelar.setOnClickListener{ dismiss() }

        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}