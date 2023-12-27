package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.data.repository.CategoriaRepository
import javax.inject.Inject

class CategoriaUseCase @Inject constructor(
    private val repository: CategoriaRepository
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getCategorias(): MutableList<Categoria>? = withContext(Dispatchers.IO){
        repository.getCategorias()
    }

    suspend fun getCategoria(idCategoria: Int): Categoria? = withContext(Dispatchers.IO){
        repository.getCategoria(idCategoria)
    }
}