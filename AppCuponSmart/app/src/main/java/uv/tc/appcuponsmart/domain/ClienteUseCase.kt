package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.data.repository.ClienteRepository
import javax.inject.Inject

class ClienteUseCase @Inject constructor(
    private val repository: ClienteRepository
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getClientes(): MutableList<Cliente>? = withContext(Dispatchers.IO){
        repository.getClientes()
    }

    suspend fun getCliente(idCliente: Int): Cliente? = withContext(Dispatchers.IO){
        repository.getCliente(idCliente)
    }

    suspend fun getCliente(correo: String): Cliente? = withContext(Dispatchers.IO){
        repository.getCliente(correo)
    }

    suspend fun addCliente(cliente: Cliente): Boolean = withContext(Dispatchers.IO){
        repository.addCliente(cliente)
    }

    suspend fun updateCliente(cliente: Cliente): Boolean = withContext(Dispatchers.IO){
        repository.updateCliente(cliente)
    }

    suspend fun deleteCliente(idCliente: Int): Boolean = withContext(Dispatchers.IO){
        repository.deleteCliente(idCliente)
    }
}