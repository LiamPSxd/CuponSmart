package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.repository.PromocionRepository
import javax.inject.Inject

class PromocionUseCase @Inject constructor(
    private val repository: PromocionRepository
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getCupones(): MutableList<Promocion>? = withContext(Dispatchers.IO){
        repository.getCupones()
    }

    suspend fun getPromocion(idPromocion: Int): Promocion? = withContext(Dispatchers.IO){
        repository.getPromocion(idPromocion)
    }
}