package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.PromocionSucursal
import uv.tc.appcuponsmart.data.repository.PromocionSucursalRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class PromocionSucursalUseCase @Inject constructor(
    private val repository: PromocionSucursalRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getPromocionesSucursales(): MutableList<PromocionSucursal>? = withContext(Dispatchers.IO){
        repository.getPromocionesSucursales()
    }

    suspend fun getPromocionesSucursalesPorPromocion(idPromocion: Int): MutableList<PromocionSucursal>? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idPromocion))
            repository.getPromocionesSucursalesPorPromocion(idPromocion)
        else null
    }
}