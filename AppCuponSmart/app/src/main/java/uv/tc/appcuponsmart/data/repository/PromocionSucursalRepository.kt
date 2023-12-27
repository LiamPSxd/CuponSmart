package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.PromocionSucursal
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaPromocionSucursal
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class PromocionSucursalRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}promocionesSucursales/"
    private var error: String? = null

    private fun detectarError(response: String?): String? =
        if(response?.contains("Error ")!!){
            error = response
            null
    }else response

    fun error(): String? = error

    suspend fun getPromocionesSucursales(): MutableList<PromocionSucursal>? = json
        .fromJson(detectarError(api.get("${url}obtenerPromocionesSucursales")), RespuestaPromocionSucursal::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.promocionesSucursales else null
            } else null
    }

    suspend fun getPromocionesSucursalesPorPromocion(idPromocion: Int): MutableList<PromocionSucursal>? = json
        .fromJson(detectarError(api.get("${url}obtenerPromocionesSucursalesPorIdPromocion/$idPromocion")), RespuestaPromocionSucursal::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.promocionesSucursales else null
            } else null
    }
}