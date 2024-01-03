package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Sucursal
import uv.tc.appcuponsmart.data.repository.SucursalRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class SucursalUseCase @Inject constructor(
    private val repository: SucursalRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getSucursales(): MutableList<Sucursal>? = withContext(Dispatchers.IO){
        repository.getSucursales()
    }

    suspend fun getSucursal(idSucursal: Int): Sucursal? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idSucursal))
            repository.getSucursal(idSucursal)
        else null
    }
}