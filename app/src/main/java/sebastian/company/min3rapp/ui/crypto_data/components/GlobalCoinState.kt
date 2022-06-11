package sebastian.company.min3rapp.ui.crypto_data.components

import sebastian.company.min3rapp.domain.model.Coin
import sebastian.company.min3rapp.domain.model.MarketData

data class GlobalCoinState(
    val isLoading: Boolean = false,
    val marketData: MarketData = MarketData(0.0),
    val error: String = ""
)
