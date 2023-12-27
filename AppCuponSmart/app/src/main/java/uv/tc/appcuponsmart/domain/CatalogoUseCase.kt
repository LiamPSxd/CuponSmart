package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Estatus
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.data.model.entidad.TipoPromocion
import uv.tc.appcuponsmart.data.repository.CatalogoRepository
import javax.inject.Inject

class CatalogoUseCase @Inject constructor(
    private val repository: CatalogoRepository
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getEstados(): MutableList<Estado>? = withContext(Dispatchers.IO){
        repository.getEstados()
    }

    suspend fun getEstado(idEstado: Int): Estado? = withContext(Dispatchers.IO){
        repository.getEstado(idEstado)
    }

    suspend fun getEstatus(): MutableList<Estatus>? = withContext(Dispatchers.IO){
        repository.getEstatus()
    }

    suspend fun getEstatus(idEstatus: Int): Estatus? = withContext(Dispatchers.IO){
        repository.getEstatus(idEstatus)
    }

    suspend fun getMunicipios(): MutableList<Municipio>? = withContext(Dispatchers.IO){
        repository.getMunicipios()
    }

    suspend fun getMunicipiosPorEstado(idEstado: Int): MutableList<Municipio>? = withContext(Dispatchers.IO){
        repository.getMunicipiosPorEstado(idEstado)
    }

    suspend fun getMunicipio(idMunicipio: Int): Municipio? = withContext(Dispatchers.IO){
        repository.getMunicipio(idMunicipio)
    }

    suspend fun getTiposPromocion(): MutableList<TipoPromocion>? = withContext(Dispatchers.IO){
        repository.getTiposPromocion()
    }

    suspend fun getTipoPromocion(idTipoPromocion: Int): TipoPromocion? = withContext(Dispatchers.IO){
        repository.getTipoPromocion(idTipoPromocion)
    }
}