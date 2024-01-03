package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaCliente
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class ClienteRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson
){
    private val url = "${Constantes.Servicios.URL_WS}clientes/"
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

    suspend fun getClientes(): MutableList<Cliente>? = json
        .fromJson(detectarError(api.get("${url}obtenerClientes")), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido else null
            } else null
    }

    suspend fun getCliente(idCliente: Int): Cliente? = json
        .fromJson(detectarError(api.get("${url}obtenerClientePorId/$idCliente")), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }

    suspend fun getCliente(correo: String): Cliente? = json
        .fromJson(detectarError(api.get("${url}obtenerClientePorCorreo/$correo")), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }

    suspend fun addCliente(cliente: Cliente): Boolean = json
        .fromJson(detectarError(api.post("${url}registrar", cliente)), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) !respuesta.error!! else false
    }

    suspend fun updateCliente(cliente: Cliente): Boolean = json
        .fromJson(detectarError(api.put("${url}modificar", cliente)), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) !respuesta.error!! else false
    }

    suspend fun deleteCliente(idCliente: Int): Boolean = json
        .fromJson(detectarError(api.delete("${url}eliminar/$idCliente")), RespuestaCliente::class.java)
        .let{ respuesta ->
            return if(respuesta != null) !respuesta.error!! else false
    }
}