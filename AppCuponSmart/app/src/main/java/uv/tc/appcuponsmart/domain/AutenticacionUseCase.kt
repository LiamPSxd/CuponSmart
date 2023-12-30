package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Cliente
import uv.tc.appcuponsmart.data.repository.AutenticacionRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class AutenticacionUseCase @Inject constructor(
    private val repository: AutenticacionRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun loginMovil(correo: String, contrasenia: String): Cliente? = withContext(Dispatchers.IO){
        if(verificaciones.cadena(correo) && verificaciones.cadena(contrasenia))
            repository.loginMovil(correo, contrasenia)
        else null
    }
}