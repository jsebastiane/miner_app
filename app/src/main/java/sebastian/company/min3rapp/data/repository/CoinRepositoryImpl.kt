package sebastian.company.min3rapp.data.repository

import sebastian.company.min3rapp.data.remote.CoinGApi
import sebastian.company.min3rapp.data.remote.dto.crypto_data.CoinDto
import sebastian.company.min3rapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinGApi
): CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }
}