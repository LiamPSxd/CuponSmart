package uv.tc.appcuponsmart.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Patterns

object FuncionHelper{
    fun validarCorreo(correo: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(correo).matches()

    fun validarCorreoNoRegistrado(correo: String, correos: MutableList<String>): Boolean =
        !correos.contains(correo)

    fun base64ToBitMap(base64: String): Bitmap?{
        return if(base64.isNotEmpty()){
            val bytes = Base64.decode(base64, Base64.DEFAULT)

            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } else null
    }
}