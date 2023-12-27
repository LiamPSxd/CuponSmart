package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaPromocion
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class PromocionRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson,
    private val verificaciones: Verificaciones
){
    private val url = "${Constantes.Servicios.URL_WS}promociones/"
    private var error: String? = null

    private fun detectarError(response: String?): String? =
        if(response?.contains("Error ")!!){
            error = response
            null
    }else response

    private fun cuponesValidos(promociones: MutableList<Promocion>?): MutableList<Promocion>? =
        if(verificaciones.listaNoVacia(promociones)){
            val formato = SimpleDateFormat(Constantes.Utileria.FORMATO_FECHA, Locale.US)

            promociones?.forEach{
                try{
                    val fechaActual = formato.parse(DateTimeFormatter.ofPattern(Constantes.Utileria.FORMATO_FECHA).format(LocalDateTime.now()))
                    val fechaTermino = formato.parse(it.fechaTermino.toString())

                    if(fechaTermino?.after(fechaActual) == false)
                        promociones.remove(it)
                }catch(_: ParseException){}
            }

            promociones
    }else null

    fun error(): String? = error

    suspend fun getCupones(): MutableList<Promocion>? = json
        .fromJson(detectarError(api.get("${url}obtenerCupones")), RespuestaPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) cuponesValidos(respuesta.promociones) else null
            } else null
    }

    suspend fun getPromocion(idPromocion: Int): Promocion? = json
        .fromJson(detectarError(api.get("${url}obtenerPromocionPorId/$idPromocion")), RespuestaPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.promociones?.get(0) else null
            } else null
    }
}