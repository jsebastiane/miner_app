package sebastian.company.min3rapp.ui.crypto_data.components

import sebastian.company.min3rapp.domain.model.Coin

interface CoinAction {
    fun onClick(coin: Coin)
}