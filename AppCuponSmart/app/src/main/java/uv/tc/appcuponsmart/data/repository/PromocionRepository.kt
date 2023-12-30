package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaPromocion
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class PromocionRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson,
    private val verificaciones: Verificaciones,
    private val empresaRepository: EmpresaRepository,
    private val tipoPromocionRepository: CatalogoRepository,
    private val mediaRepository: MediaRepository,
    private val promocionSucursalRepository: PromocionSucursalRepository,
    private val sucursalRepository: SucursalRepository
){
    private val url = "${Constantes.Servicios.URL_WS}promociones/"
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

    private fun obtenerVigencia(cupon: Promocion){
        val formato = SimpleDateFormat(Constantes.Utileria.FORMATO_FECHA, Locale.getDefault())
        val fI = formato.parse(cupon.fechaInicio ?: "")
        val fT = formato.parse(cupon.fechaTermino ?: "")

        formato.applyPattern(Constantes.Utileria.FORMATO_VIGENCIA)
        val fechaInicio = if(fI != null) formato.format(fI) else "-/-/-"
        val fechaTermino = if(fT != null) formato.format(fT) else "-/-/-"

        cupon.vigencia = "$fechaInicio a $fechaTermino"
    }

    private suspend fun prepararCupones(cupones: MutableList<Promocion>?){
        if(verificaciones.listaNoVacia(cupones)){
            cupones?.forEach{ cupon ->
                withContext(Dispatchers.Unconfined){
                    obtenerVigencia(cupon)
                }

                withContext(Dispatchers.IO){
                    empresaRepository.getEmpresa(cupon.idEmpresa!!)
                }?.let{ empresa ->
                    cupon.empresa = empresa.nombre
                }

                withContext(Dispatchers.IO){
                    tipoPromocionRepository.getTipoPromocion(cupon.idTipoPromocion!!)
                }?.let{ tipo ->
                    cupon.tipo = tipo.tipo
                }

                withContext(Dispatchers.IO){
                    mediaRepository.getImagenPromocion(cupon.id!!)
                }?.let{ imagenBase64 ->
                    cupon.imagenBase64 = imagenBase64
                }

                withContext(Dispatchers.IO){
                    promocionSucursalRepository.getPromocionesSucursalesPorPromocion(cupon.id!!)?.forEach{ promoSuc ->
                        promoSuc.idSucursal?.let{ idSucursal ->
                            withContext(Dispatchers.IO){
                                sucursalRepository.getSucursal(idSucursal)
                            }?.let{
                                cupon.sucursales?.add(it)
                            }
                        }
                    }
                }

                when(cupon.tipo){
                    "Descuento" -> cupon.valorTipo = "${cupon.valor}% de ${cupon.tipo}"
                    "Costo Rebajado" -> cupon.valorTipo = "$${cupon.valor} de ${cupon.tipo}"
                }
            }
        }
    }

    private suspend fun cuponesValidos(promociones: MutableList<Promocion>?): MutableList<Promocion>? =
        if(verificaciones.listaNoVacia(promociones)){
            val formato = SimpleDateFormat(Constantes.Utileria.FORMATO_FECHA, Locale.getDefault())
            val tiempoActual = DateTimeFormatter.ofPattern(Constantes.Utileria.FORMATO_FECHA).format(LocalDate.now())
            val cupones = mutableListOf<Promocion>()

            promociones?.forEach{ promo ->
                try{
                    val fechaActual = formato.parse(tiempoActual)
                    val fechaTermino = formato.parse(promo.fechaTermino ?: "")

                    if(fechaTermino?.after(fechaActual) == true)
                        cupones.add(promo)
                }catch(_: ParseException){}
            }

            prepararCupones(cupones)
            cupones
    }else null

    fun error(): String? = error

    suspend fun getCupones(): MutableList<Promocion>? = json
        .fromJson(detectarError(api.get("${url}obtenerCupones")), RespuestaPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) cuponesValidos(respuesta.contenido) else null
            } else null
    }

    suspend fun getPromocion(idPromocion: Int): Promocion? = json
        .fromJson(detectarError(api.get("${url}obtenerPromocionPorId/$idPromocion")), RespuestaPromocion::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) respuesta.contenido?.get(0) else null
            } else null
    }
}