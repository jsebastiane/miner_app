package sebastian.company.min3rapp.data.repository

import sebastian.company.min3rapp.data.remote.CoinGApi
import sebastian.company.min3rapp.data.remote.dto.crypto_data.GlobalDataDto
import sebastian.company.min3rapp.domain.repository.GlobalCoinRepository
import javax.inject.Inject

class GlobalCoinImpl @Inject constructor(
    private val api: CoinGApi
): GlobalCoinRepository {
    override suspend fun getGlobalData(): GlobalDataDto {
        return api.getGlobalData()
    }
}