package uv.tc.appcuponsmart.core

import androidx.fragment.app.FragmentManager
import uv.tc.appcuponsmart.ui.view.dialog.Mensaje
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MensajeHelper @Inject constructor(){
    fun mostrarMensaje(fragmentManager: FragmentManager, titulo: String, contenido: String){
        Mensaje(titulo, contenido).show(fragmentManager, "Mensaje")
    }
}