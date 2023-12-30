package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.data.repository.PromocionRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class PromocionUseCase @Inject constructor(
    private val repository: PromocionRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getCupones(): MutableList<Promocion>? = withContext(Dispatchers.IO){
        repository.getCupones()
    }

    suspend fun getPromocion(idPromocion: Int): Promocion? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idPromocion))
            repository.getPromocion(idPromocion)
        else null
    }
}