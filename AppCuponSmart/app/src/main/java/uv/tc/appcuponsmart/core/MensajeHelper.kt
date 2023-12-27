package uv.tc.appcuponsmart.core

import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.FragmentManager
import uv.tc.appcuponsmart.ui.view.dialog.Mensaje
import uv.tc.appcuponsmart.ui.view.dialog.MensajeCerrarSesion
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MensajeHelper @Inject constructor(
    private val mensaje: Mensaje,
    private val mensajeCerrarSesion: MensajeCerrarSesion
){
    fun mostrarMensaje(fragmentManager: FragmentManager, titulo: String, contenido: String){
        mensaje.titulo = titulo
        mensaje.contenido = contenido
        mensaje.show(fragmentManager, "Mensaje")
    }

    fun mostrarMensajeCerrarSesion(activity: AppCompatActivity, dataStore: DataStore<Preferences>, titulo: String, contenido: String){
        mensajeCerrarSesion.activity = activity
        mensajeCerrarSesion.dataStore = dataStore
        mensajeCerrarSesion.titulo = titulo
        mensajeCerrarSesion.contenido = contenido
        mensajeCerrarSesion.show(activity.supportFragmentManager, "MensajeCerrarSesion")
    }
}