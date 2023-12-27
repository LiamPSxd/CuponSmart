package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.data.repository.SucursalRepository
import javax.inject.Inject

class SucursalUseCase @Inject constructor(
    private val repository: SucursalRepository
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getSucursales(): MutableList<Sucursal>? = withContext(Dispatchers.IO){
        repository.getSucursales()
    }

    suspend fun getSucursal(idSucursal: Int): Sucursal? = withContext(Dispatchers.IO){
        repository.getSucursal(idSucursal)
    }
}