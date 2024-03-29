package uv.tc.appcuponsmart.di

import android.graphics.Bitmap
import uv.tc.appcuponsmart.core.FuncionHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Verificaciones @Inject constructor(
    private val funciones: FuncionHelper
){
    fun isClassNull(clase: Any?): Boolean =
        clase?.equals(null) == true

    fun numerico(numero: Number): Boolean =
        !isClassNull(numero) && numero.toInt() > 0

    fun cadena(cadena: String?): Boolean =
        !isClassNull(cadena) && cadena?.isNotEmpty() == true

    fun listaNoVacia(lista: List<Any>?): Boolean =
        !isClassNull(lista) && lista?.isNotEmpty() == true

    fun validarCorreo(correo: String): Boolean =
        funciones.validarCorreo(correo)

    fun byteArrayToBase64(byteArray: ByteArray): String? =
        funciones.byteArrayToBase64(byteArray)

    fun base64ToByteArray(base64: String): ByteArray =
        funciones.base64ToByteArray(base64)

    fun base64ToBitMap(base64: String): Bitmap? =
        funciones.base64ToBitMap(base64)
}