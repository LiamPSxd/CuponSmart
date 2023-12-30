package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaDireccion
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class DireccionRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}direcciones/"
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

    suspend fun getDirecciones(): MutableList<Direccion>? = json
        .fromJson(detectarError(api.get("${url}obtenerDirecciones")), RespuestaDireccion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido else null
            } else null
    }

    suspend fun getDireccion(idDireccion: Int): Direccion? = json
        .fromJson(detectarError(api.get("${url}obtenerDireccionPorId/$idDireccion")), RespuestaDireccion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }

    suspend fun addDireccion(direccion: Direccion): Boolean = json
        .fromJson(detectarError(api.post("${url}registrar", direccion)), RespuestaDireccion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) !respuesta.error!! else false
    }

    suspend fun updateDireccion(direccion: Direccion): Boolean = json
        .fromJson(detectarError(api.put("${url}modificar", direccion)), RespuestaDireccion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) !respuesta.error!! else false
    }

    suspend fun deleteDireccion(idDireccion: Int): Boolean = json
        .fromJson(detectarError(api.delete("${url}eliminar/$idDireccion")), RespuestaDireccion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) !respuesta.error!! else false
    }
}