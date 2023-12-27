package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaSucursal
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class SucursalRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}sucursales/"
    private var error: String? = null

    private fun detectarError(response: String?): String? =
        if(response?.contains("Error ")!!){
            error = response
            null
    }else response

    fun error(): String? = error

    suspend fun getSucursales(): MutableList<Sucursal>? = json
        .fromJson(detectarError(api.get("${url}obtenerSucursales")), RespuestaSucursal::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.sucursales else null
            } else null
    }

    suspend fun getSucursal(idSucursal: Int): Sucursal? = json
        .fromJson(detectarError(api.get("${url}obtenerSucursalPorId/$idSucursal")), RespuestaSucursal::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.sucursales?.get(0) else null
            } else null
    }
}