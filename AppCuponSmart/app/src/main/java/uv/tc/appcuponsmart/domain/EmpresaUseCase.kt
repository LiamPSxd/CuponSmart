package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Empresa
import uv.tc.appcuponsmart.data.repository.EmpresaRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class EmpresaUseCase @Inject constructor(
    private val repository: EmpresaRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getEmpresas(): MutableList<Empresa>? = withContext(Dispatchers.IO){
        repository.getEmpresas()
    }

    suspend fun getEmpresa(idEmpresa: Int): Empresa? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idEmpresa))
            repository.getEmpresa(idEmpresa)
        else null
    }
}