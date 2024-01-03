package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.repository.DataStoreRepository
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

class DataStoreUseCase @Inject constructor(
    private val repository: DataStoreRepository
){
    suspend fun getIdCliente() = withContext(Dispatchers.IO){
        repository.getInt(Constantes.DataStore.ID_CLIENTE)
    }

    suspend fun putIdCliente(idCliente: Int) = withContext(Dispatchers.IO){
        repository.putInt(Constantes.DataStore.ID_CLIENTE, idCliente)
    }

    suspend fun getNombreCliente() = withContext(Dispatchers.IO){
        repository.getString(Constantes.DataStore.NOMBRE_CLIENTE)
    }

    suspend fun putNombreCliente(nombre: String) = withContext(Dispatchers.IO){
        repository.putString(Constantes.DataStore.NOMBRE_CLIENTE, nombre)
    }

    suspend fun getPromocion() = withContext(Dispatchers.IO){
        repository.getString(Constantes.DataStore.PROMOCION)
    }

    suspend fun putPromocion(promocion: String) = withContext(Dispatchers.IO){
        repository.putString(Constantes.DataStore.PROMOCION, promocion)
    }

    suspend fun getSucursal() = withContext(Dispatchers.IO){
        repository.getString(Constantes.DataStore.SUCURSAL)
    }

    suspend fun putSucursal(sucursal: String) = withContext(Dispatchers.IO){
        repository.putString(Constantes.DataStore.SUCURSAL, sucursal)
    }
}