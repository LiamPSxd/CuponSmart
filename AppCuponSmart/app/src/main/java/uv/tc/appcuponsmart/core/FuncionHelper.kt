package uv.tc.appcuponsmart.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Patterns

object FuncionHelper{
    fun validarCorreo(correo: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(correo).matches()

    fun byteArrayToBase64(byteArray: ByteArray): String? =
        if(byteArray.isNotEmpty()){
            Base64.encodeToString(byteArray, Base64.DEFAULT)
    } else null

    fun base64ToByteArray(base64: String): ByteArray =
        if(base64.isNotEmpty()){
            Base64.decode(base64, Base64.DEFAULT)
    } else byteArrayOf()

    fun base64ToBitMap(base64: String): Bitmap? =
        if(base64.isNotEmpty()){
            val bytes = base64ToByteArray(base64)

            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } else null
}