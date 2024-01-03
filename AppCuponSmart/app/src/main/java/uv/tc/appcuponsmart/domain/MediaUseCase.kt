package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.repository.MediaRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class MediaUseCase @Inject constructor(
    private val repository: MediaRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getImagenPromocion(idPromocion: Int): String? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idPromocion))
            repository.getImagenPromocion(idPromocion)
        else null
    }

    suspend fun getFotoCliente(idCliente: Int): String? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idCliente))
            repository.getFotoCliente(idCliente)
        else null
    }

    suspend fun addFotoCliente(idCliente: Int, foto: ByteArray): Boolean = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idCliente) && foto.isNotEmpty())
            repository.addFotoCliente(idCliente, foto)
        else false
    }
}