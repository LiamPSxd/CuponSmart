package uv.tc.appcuponsmart.ui.viewmodel.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.domain.DataStoreUseCase
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val useCase: DataStoreUseCase
): ViewModel(){
    val idCliente = MutableLiveData<Int>()

    fun putIdCliente(idCliente: Int) = viewModelScope.launch(Dispatchers.IO){
        useCase.putIdCliente(idCliente)
    }

    fun getIdCliente() = viewModelScope.launch(Dispatchers.IO){
        useCase.getIdCliente().collect{
            idCliente.postValue(it)
        }
    }
}