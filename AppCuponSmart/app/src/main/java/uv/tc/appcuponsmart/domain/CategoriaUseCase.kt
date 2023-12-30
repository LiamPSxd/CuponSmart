package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.data.repository.CategoriaRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class CategoriaUseCase @Inject constructor(
    private val repository: CategoriaRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getCategorias(): MutableList<Categoria>? = withContext(Dispatchers.IO){
        repository.getCategorias()
    }

    suspend fun getCategoria(idCategoria: Int): Categoria? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idCategoria))
            repository.getCategoria(idCategoria)
        else null
    }
}