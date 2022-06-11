package sebastian.company.min3rapp.data.remote.dto.crypto_data

import com.google.gson.annotations.SerializedName
import sebastian.company.min3rapp.domain.model.CoinMarketShare
import sebastian.company.min3rapp.domain.model.MarketData

data class MarketDataDto(
    @SerializedName("market_cap_change_percentage_24h_usd")
    val marketCapChange24h: Double,
    @SerializedName("market_cap_percentage")
    val marketShareMap: Map<String, Double>

)

fun MarketDataDto.toMarketData(): MarketData{
    val marketShareList = mutableListOf<CoinMarketShare>()
    marketShareMap.entries.forEach { marketShareList.add(CoinMarketShare(it.key, it.value)) }
    return MarketData(marketCapChange24h = marketCapChange24h,
    marketShareMap = marketShareList)
}