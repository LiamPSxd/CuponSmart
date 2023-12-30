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

    private fun detectarError(response: String?): String?{
        if(!response.isNullOrEmpty()){
            if(response.contains("Error ")){
                error = response
                return null
            }

            if(response.contains("<!DOCTYPE ")){
                error = Constantes.Excepciones.PETICION
                return null
            }
        }

        return response
    }

    fun error(): String? = error

    suspend fun getImagenPromocion(idPromocion: Int): String? = json
        .fromJson(detectarError(api.get("${url}obtenerImagenPromocion/$idPromocion")), RespuestaPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0)?.imagenBase64 else null
            }else null
    }

    suspend fun getFotoCliente(idCliente: Int): String? = json
        .fromJson(detectarError(api.get("${url}obtenerFotoCliente/$idCliente")), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0)?.fotoBase64 else null
            }else null
    }

    suspend fun addFotoCliente(idCliente: Int, foto: ByteArray): Boolean = json
        .fromJson(detectarError(api.putMedia("${url}registrarFotoCliente/$idCliente", foto)), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) !respuesta.error!! else false
    }
}