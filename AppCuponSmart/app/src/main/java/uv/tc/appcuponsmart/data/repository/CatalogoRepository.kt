package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Estatus
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.data.model.entidad.TipoPromocion
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaEstado
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaEstatus
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaMunicipio
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaTipoPromocion
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class CatalogoRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}catalogo/"
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

    suspend fun getEstados(): MutableList<Estado>? = json
        .fromJson(detectarError(api.get("${url}obtenerEstados")), RespuestaEstado::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido else null
            } else null
    }

    suspend fun getEstado(idEstado: Int): Estado? = json
        .fromJson(detectarError(api.get("${url}obtenerEstadoPorId/$idEstado")), RespuestaEstado::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }

    suspend fun getEstatus(): MutableList<Estatus>? = json
        .fromJson(detectarError(api.get("${url}obtenerEstatus")), RespuestaEstatus::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido else null
            } else null
    }

    suspend fun getEstatus(idEstatus: Int): Estatus? = json
        .fromJson(detectarError(api.get("${url}obtenerEstatusPorId/$idEstatus")), RespuestaEstatus::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }

    suspend fun getMunicipios(): MutableList<Municipio>? = json
        .fromJson(detectarError(api.get("${url}obtenerMunicipios")), RespuestaMunicipio::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido else null
            } else null
    }

    suspend fun getMunicipiosPorEstado(idEstado: Int): MutableList<Municipio>? = json
        .fromJson(detectarError(api.get("${url}obtenerMunicipiosPorEstado/$idEstado")), RespuestaMunicipio::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido else null
            } else null
    }

    suspend fun getMunicipio(idMunicipio: Int): Municipio? = json
        .fromJson(detectarError(api.get("${url}obtenerMunicipioPorId/$idMunicipio")), RespuestaMunicipio::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }

    suspend fun getTiposPromocion(): MutableList<TipoPromocion>? = json
        .fromJson(detectarError(api.get("${url}obtenerTiposPromocion")), RespuestaTipoPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido else null
            } else null
    }

    suspend fun getTipoPromocion(idTipoPromocion: Int): TipoPromocion? = json
        .fromJson(detectarError(api.get("${url}obtenerTipoPromocionPorId/$idTipoPromocion")), RespuestaTipoPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }
}