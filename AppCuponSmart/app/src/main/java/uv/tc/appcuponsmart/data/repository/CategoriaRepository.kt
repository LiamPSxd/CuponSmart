package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaCategoria
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class CategoriaRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}categorias/"
    private var error: String? = null

    private fun detectarError(response: String?): String? =
        if(response?.contains("Error ")!!){
            error = response
            null
    }else response

    fun error(): String? = error

    suspend fun getCategorias(): MutableList<Categoria>? = json
        .fromJson(detectarError(api.get("${url}obtenerCategorias")), RespuestaCategoria::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.categorias else null
            } else null
    }

    suspend fun getCategoria(idCategoria: Int): Categoria? = json
        .fromJson(detectarError(api.get("${url}obtenerCategoriaPorId/$idCategoria")), RespuestaCategoria::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.categorias?.get(0) else null
            } else null
    }
}