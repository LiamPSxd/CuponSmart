package uv.tc.appcuponsmart.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.appcuponsmart.data.model.entidad.Categoria
import uv.tc.appcuponsmart.domain.CategoriaUseCase
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val useCase: CategoriaUseCase
): ViewModel(){
    val categorias = MutableLiveData<MutableList<Categoria>?>()
    val categoria = MutableLiveData<Categoria?>()
    val error = MutableLiveData<String?>()

    private suspend fun getError() = error.postValue(useCase())

    fun getCategorias() = viewModelScope.launch(Dispatchers.IO){
        categorias.postValue(useCase.getCategorias())
        getError()
    }

    fun getCategoria(idCategoria: Int) = viewModelScope.launch(Dispatchers.IO){
        categoria.postValue(useCase.getCategoria(idCategoria))
        getError()
    }
}