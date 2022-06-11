package sebastian.company.min3rapp.ui.crypto_data.components

import sebastian.company.min3rapp.domain.model.Coin

data class CoinsListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)
