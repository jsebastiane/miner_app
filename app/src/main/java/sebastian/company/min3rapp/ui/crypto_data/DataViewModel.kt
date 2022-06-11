package sebastian.company.min3rapp.ui.crypto_data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sebastian.company.min3rapp.common.Resource
import sebastian.company.min3rapp.domain.model.MarketData
import sebastian.company.min3rapp.domain.use_case.GetGlobalDataUseCase
import sebastian.company.min3rapp.ui.crypto_data.components.GlobalCoinState
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val getGlobalDataUseCase: GetGlobalDataUseCase
): ViewModel() {
    private val _globalCoinState = MutableStateFlow(GlobalCoinState())
    val globalCoinState = _globalCoinState.asStateFlow()

    fun getGlobalCoinData(){
        getGlobalDataUseCase().onEach { result ->
            when(result){
                is Resource.Success ->{
                    _globalCoinState.value = GlobalCoinState(marketData = result.data ?: MarketData(0.0))
                }
                is Resource.Error ->{
                    _globalCoinState.value = GlobalCoinState(error = result.message ?: "Unexpected error occurred")
                }
                is Resource.Loading ->{
                    _globalCoinState.value = GlobalCoinState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}