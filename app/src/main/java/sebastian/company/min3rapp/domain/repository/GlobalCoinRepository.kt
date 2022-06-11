package sebastian.company.min3rapp.domain.repository

import sebastian.company.min3rapp.data.remote.dto.crypto_data.GlobalDataDto

interface GlobalCoinRepository {

    suspend fun getGlobalData():GlobalDataDto
}