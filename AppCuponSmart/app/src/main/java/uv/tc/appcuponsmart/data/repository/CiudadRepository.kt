package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaCiudad
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class CiudadRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}ciudades/"
    private var error: String? = null

    private fun detectarError(response: String?): String? =
        if(response?.contains("Error ")!!){
            error = response
            null
    }else response

    fun error(): String? = error

    suspend fun getCiudades(): MutableList<Ciudad>? = json
        .fromJson(detectarError(api.get("${url}obtenerCiudades")), RespuestaCiudad::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.ciudades else null
            } else null
    }

    suspend fun getCiudadesPorMunicipio(idMunicipio: Int): MutableList<Ciudad>? = json
        .fromJson(detectarError(api.get("${url}obtenerCiudadesPorIdMunicipio/$idMunicipio")), RespuestaCiudad::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.ciudades else null
            } else null
    }

    suspend fun getCiudad(idCiudad: Int): Ciudad? = json
        .fromJson(detectarError(api.get("${url}obtenerCiudadPorId/$idCiudad")), RespuestaCiudad::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.ciudades?.get(0) else null
            } else null
    }
}