package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Promocion
import uv.tc.appcuponsmart.domain.PromocionUseCase
import javax.inject.Inject

@HiltViewModel
class PromocionViewModel @Inject constructor(
    private val useCase: PromocionUseCase
): ViewModel(){
    val cupones = MutableLiveData<MutableList<Promocion>?>()
    val promocion = MutableLiveData<Promocion?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getCupones() = viewModelScope.launch(Dispatchers.IO){
        cupones.postValue(useCase.getCupones())
        getError()
    }

    fun getPromocion(idPromocion: Int) = viewModelScope.launch(Dispatchers.IO){
        promocion.postValue(useCase.getPromocion(idPromocion))
        getError()
    }
}