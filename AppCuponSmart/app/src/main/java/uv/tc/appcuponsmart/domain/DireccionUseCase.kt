package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Direccion
import uv.tc.appcuponsmart.data.repository.DireccionRepository
import javax.inject.Inject

class DireccionUseCase @Inject constructor(
    private val repository: DireccionRepository
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getDirecciones(): MutableList<Direccion>? = withContext(Dispatchers.IO){
        repository.getDirecciones()
    }

    suspend fun getDireccion(idDireccion: Int): Direccion? = withContext(Dispatchers.IO){
        repository.getDireccion(idDireccion)
    }

    suspend fun addDireccion(direccion: Direccion): Boolean = withContext(Dispatchers.IO){
        repository.addDireccion(direccion)
    }

    suspend fun updateDireccion(direccion: Direccion): Boolean = withContext(Dispatchers.IO){
        repository.updateDireccion(direccion)
    }

    suspend fun deleteDireccion(idDireccion: Int): Boolean = withContext(Dispatchers.IO){
        repository.deleteDireccion(idDireccion)
    }
}