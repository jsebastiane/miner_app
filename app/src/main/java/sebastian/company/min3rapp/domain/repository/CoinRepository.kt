package sebastian.company.min3rapp.domain.repository

import sebastian.company.min3rapp.data.remote.dto.crypto_data.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>
}