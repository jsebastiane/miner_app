package sebastian.company.min3rapp.data.remote

import retrofit2.http.GET
import sebastian.company.min3rapp.data.remote.dto.crypto_data.CoinDto
import sebastian.company.min3rapp.data.remote.dto.crypto_data.GlobalDataDto

interface CoinGApi {

    @GET("v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=true&price_change_percentage=24h")
    suspend fun getCoins(): List<CoinDto>

    @GET("v3/global")
    suspend fun getGlobalData(): GlobalDataDto
}