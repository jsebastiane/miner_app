package sebastian.company.min3rapp.ui.crypto_data.coin_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import sebastian.company.min3rapp.common.Resource
import sebastian.company.min3rapp.domain.model.Coin
import sebastian.company.min3rapp.domain.use_case.GetCoinsUseCase
import sebastian.company.min3rapp.ui.crypto_data.components.CoinsListState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CoinPricesViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
): ViewModel() {

    private var tempCoins: List<Coin> = listOf<Coin>()
    private val _coinsState = MutableStateFlow(CoinsListState())
    val coinsState = _coinsState.asStateFlow()
    var searchQuery: String? = null

    fun getCoins(){
        getCoinsUseCase().onEach { result ->
            when(result){
                is Resource.Success ->{
                    tempCoins = result.data ?: emptyList()
                    filterCoins()
                }

                is Resource.Error ->{
                    _coinsState.value = CoinsListState(error = result.message ?: "Unexpected error" +
                    "occurred")
                }
                is Resource.Loading ->{
                    _coinsState.value = CoinsListState(isLoading = true)
                }

            }
        }.launchIn(viewModelScope)
    }

    fun filterCoins(){
        val tempList = tempCoins.filter {
            it.name?.lowercase()?.contains(searchQuery?.lowercase() ?: "")!!}
        _coinsState.value = CoinsListState(coins = tempList)

    }
}