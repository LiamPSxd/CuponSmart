package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.data.repository.DireccionRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class DireccionUseCase @Inject constructor(
    private val repository: DireccionRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getDirecciones(): MutableList<Direccion>? = withContext(Dispatchers.IO){
        repository.getDirecciones()
    }

    suspend fun getDireccion(idDireccion: Int): Direccion? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idDireccion))
            repository.getDireccion(idDireccion)
        else null
    }

    suspend fun addDireccion(direccion: Direccion): Boolean = withContext(Dispatchers.IO){
        if(!verificaciones.isClassNull(direccion))
            repository.addDireccion(direccion)
        else false
    }

    suspend fun updateDireccion(direccion: Direccion): Boolean = withContext(Dispatchers.IO){
        if(!verificaciones.isClassNull(direccion))
            repository.updateDireccion(direccion)
        else false
    }

    suspend fun deleteDireccion(idDireccion: Int): Boolean = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idDireccion))
            repository.deleteDireccion(idDireccion)
        else false
    }
}