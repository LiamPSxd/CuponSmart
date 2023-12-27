package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.repository.MediaRepository
import javax.inject.Inject

class MediaUseCase @Inject constructor(
    private val repository: MediaRepository
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getImagenPromocion(idPromocion: Int): String? = withContext(Dispatchers.IO){
        repository.getImagenPromocion(idPromocion)
    }

    suspend fun getFotoCliente(idCliente: Int): String? = withContext(Dispatchers.IO){
        repository.getFotoCliente(idCliente)
    }

    suspend fun addFotoCliente(idCliente: Int, foto: ByteArray): Boolean = withContext(Dispatchers.IO){
        repository.addFotoCliente(idCliente, foto)
    }
}