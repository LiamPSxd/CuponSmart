package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.data.repository.ClienteRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class ClienteUseCase @Inject constructor(
    private val repository: ClienteRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getClientes(): MutableList<Cliente>? = withContext(Dispatchers.IO){
        repository.getClientes()
    }

    suspend fun getCliente(idCliente: Int): Cliente? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idCliente))
            repository.getCliente(idCliente)
        else null
    }

    suspend fun getCliente(correo: String): Cliente? = withContext(Dispatchers.IO){
        if(verificaciones.cadena(correo))
            repository.getCliente(correo)
        else null
    }

    suspend fun addCliente(cliente: Cliente): Boolean = withContext(Dispatchers.IO){
        if(!verificaciones.isClassNull(cliente))
            repository.addCliente(cliente)
        else false
    }

    suspend fun updateCliente(cliente: Cliente): Boolean = withContext(Dispatchers.IO){
        if(!verificaciones.isClassNull(cliente))
            repository.updateCliente(cliente)
        else false
    }

    suspend fun deleteCliente(idCliente: Int): Boolean = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idCliente))
            repository.deleteCliente(idCliente)
        else false
    }
}