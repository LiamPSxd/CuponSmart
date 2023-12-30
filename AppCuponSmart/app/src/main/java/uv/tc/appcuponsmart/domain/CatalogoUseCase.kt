package uv.tc.appcuponsmart.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uv.tc.appcuponsmart.data.model.entidad.Estado
import uv.tc.appcuponsmart.data.model.entidad.Estatus
import uv.tc.appcuponsmart.data.model.entidad.Municipio
import uv.tc.appcuponsmart.data.model.entidad.TipoPromocion
import uv.tc.appcuponsmart.data.repository.CatalogoRepository
import uv.tc.appcuponsmart.di.Verificaciones
import javax.inject.Inject

class CatalogoUseCase @Inject constructor(
    private val repository: CatalogoRepository,
    private val verificaciones: Verificaciones
){
    suspend operator fun invoke(): String? = withContext(Dispatchers.IO){
        repository.error()
    }

    suspend fun getEstados(): MutableList<Estado>? = withContext(Dispatchers.IO){
        repository.getEstados()
    }

    suspend fun getEstado(idEstado: Int): Estado? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idEstado))
            repository.getEstado(idEstado)
        else null
    }

    suspend fun getEstatus(): MutableList<Estatus>? = withContext(Dispatchers.IO){
        repository.getEstatus()
    }

    suspend fun getEstatus(idEstatus: Int): Estatus? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idEstatus))
            repository.getEstatus(idEstatus)
        else null
    }

    suspend fun getMunicipios(): MutableList<Municipio>? = withContext(Dispatchers.IO){
        repository.getMunicipios()
    }

    suspend fun getMunicipiosPorEstado(idEstado: Int): MutableList<Municipio>? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idEstado))
            repository.getMunicipiosPorEstado(idEstado)
        else null
    }

    suspend fun getMunicipio(idMunicipio: Int): Municipio? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idMunicipio))
            repository.getMunicipio(idMunicipio)
        else null
    }

    suspend fun getTiposPromocion(): MutableList<TipoPromocion>? = withContext(Dispatchers.IO){
        repository.getTiposPromocion()
    }

    suspend fun getTipoPromocion(idTipoPromocion: Int): TipoPromocion? = withContext(Dispatchers.IO){
        if(verificaciones.numerico(idTipoPromocion))
            repository.getTipoPromocion(idTipoPromocion)
        else null
    }
}