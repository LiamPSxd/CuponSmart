package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.PromocionSucursal
import uv.tc.appcuponsmart.domain.PromocionSucursalUseCase
import javax.inject.Inject

@HiltViewModel
class PromocionSucursalViewModel @Inject constructor(
    private val useCase: PromocionSucursalUseCase
): ViewModel(){
    val promocionesSucursales = MutableLiveData<MutableList<PromocionSucursal>?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getPromocionesSucursales() = viewModelScope.launch(Dispatchers.IO){
        promocionesSucursales.postValue(useCase.getPromocionesSucursales())
        getError()
    }

    fun getPromocionesSucursalesPorPromocion(idPromocion: Int) = viewModelScope.launch(Dispatchers.IO){
        promocionesSucursales.postValue(useCase.getPromocionesSucursalesPorPromocion(idPromocion))
        getError()
    }
}