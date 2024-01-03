package uv.tc.appcuponsmart.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.data.model.respuesta.RespuestaSucursal
import uv.tc.appcuponsmart.data.network.ApiService
import uv.tc.appcuponsmart.di.Constantes
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class SucursalRepository @Inject constructor(
    private val api: ApiService,
    private val json: Gson,
    private val verificaciones: Verificaciones,
    private val direccionRepository: DireccionRepository,
    private val catalogoRepository: CatalogoRepository,
    private val ciudadRepository: CiudadRepository
){
    private val url = "${Constantes.Servicios.URL_WS}sucursales/"
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

    private suspend fun prepararSucursal(sucursal: Sucursal?): Sucursal? =
        if(!verificaciones.isClassNull(sucursal)){
            withContext(Dispatchers.IO){
                direccionRepository.getDireccion(sucursal!!.idDireccion!!)
            }?.let{ direccion ->
                direccion.latitud?.let{ latitud ->
                    sucursal!!.coordenadas[Constantes.Utileria.LATITUD] = latitud.toDouble()
                }

                direccion.longitud?.let{ longitud ->
                    sucursal!!.coordenadas[Constantes.Utileria.LONGITUD] = longitud.toDouble()
                }

                sucursal!!.direccionPromo = "${direccion.calle}, No. ${direccion.numero}, Col. ${direccion.colonia}"
                sucursal.direccion = "${direccion.calle}, No. ${direccion.numero}\nCol. ${direccion.colonia}, ${direccion.codigoPostal}"

                withContext(Dispatchers.IO){
                    ciudadRepository.getCiudad(direccion.idCiudad!!)
                }?.let{ ciudad ->
                    sucursal.ubicacion = ciudad.nombre

                    withContext(Dispatchers.IO){
                        catalogoRepository.getMunicipio(ciudad.idMunicipio!!)
                    }?.let{ municipio ->
                        sucursal.ubicacion = "${sucursal.ubicacion}, ${municipio.nombre}"

                        withContext(Dispatchers.IO){
                            catalogoRepository.getEstado(municipio.idEstado!!)
                        }?.let{ estado ->
                            sucursal.ubicacion = "${sucursal.ubicacion}, ${estado.nombre}"
                        }
                    }
                }
            }

            sucursal
    }else null

    private suspend fun prepararSucursales(sucursales: MutableList<Sucursal>?): MutableList<Sucursal>? =
        if(verificaciones.listaNoVacia(sucursales)){
            sucursales?.forEach{ sucursal ->
                prepararSucursal(sucursal)
            }

            sucursales
    }else null

    fun error(): String? = error

    suspend fun getSucursales(): MutableList<Sucursal>? = json
        .fromJson(detectarError(api.get("${url}obtenerSucursales")), RespuestaSucursal::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) prepararSucursales(respuesta.contenido) else null
            } else null
    }

    suspend fun getSucursal(idSucursal: Int): Sucursal? = json
        .fromJson(detectarError(api.get("${url}obtenerSucursalPorId/$idSucursal")), RespuestaSucursal::class.java)
        .let{ respuesta ->
            return if(respuesta != null) respuesta.error?.let{ error ->
                if(!error) prepararSucursal(respuesta.contenido?.get(0)) else null
            } else null
    }
}