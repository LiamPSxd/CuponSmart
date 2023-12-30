package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Ciudad
import uv.tc.appcuponsmart.data.repository.CiudadRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class CiudadUseCase @Inject constructor(
    private val repository: CiudadRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getCiudades(): MutableList<Ciudad>? = withContext(Dispatchers.IO){
        repository.getCiudades()
    }

    suspend fun getCiudadesPorMunicipio(idMunicipio: Int): MutableList<Ciudad>? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idMunicipio))
            repository.getCiudadesPorMunicipio(idMunicipio)
        else null
    }

    suspend fun getCiudad(idCiudad: Int): Ciudad? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idCiudad))
            repository.getCiudad(idCiudad)
        else null
    }
}