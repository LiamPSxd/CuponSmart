package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaCliente
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaPromocion
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class MediaRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}media/"
    private var error: String? = null

    private fun detectarError(response: String?): String? =
        if(response?.contains("Error ")!!){
            error = response
            null
    }else response

    fun error(): String? = error

    suspend fun getImagenPromocion(idPromocion: Int): String? = json
        .fromJson(detectarError(api.get("${url}obtenerImagenPromocion/$idPromocion")), RespuestaPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.promociones?.get(0)?.imagenBase64 else null
            }else null
    }

    suspend fun getFotoCliente(idCliente: Int): String? = json
        .fromJson(detectarError(api.get("${url}obtenerFotoCliente/$idCliente")), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.clientes?.get(0)?.fotoBase64 else null
            }else null
    }

    suspend fun addFotoCliente(idCliente: Int, foto: ByteArray): Boolean = json
        .fromJson(detectarError(api.putMedia("${url}registrarFotoCliente/$idCliente", foto)), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error!! else false
    }
}