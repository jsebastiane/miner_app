package sebastian.company.min3rapp.data.remote.dto.crypto_data


import com.google.gson.annotations.SerializedName
import sebastian.company.min3rapp.domain.model.Coin

data class CoinDto(
    val ath: Double,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double,
    @SerializedName("ath_date")
    val athDate: String,
    val atl: Double,
    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double,
    @SerializedName("atl_date")
    val atlDate: String,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double,
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Long,
    @SerializedName("high_24h")
    val high24h: Double,
    val id: String,
    val image: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("low_24h")
    val low24h: Double,
    @SerializedName("market_cap")
    val marketCap: Long,
//    @SerializedName("market_cap_change_24h")
//    val marketCapChange24h: Long,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("max_supply")
    val maxSupply: Double,
    val name: String,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: Double,
    val roi: Any,
    @SerializedName("sparkline_in_7d")
    val sparklineIn7d: SparklineDataPoints,
    val symbol: String,
    @SerializedName("total_supply")
    val totalSupply: Double,

)

fun CoinDto.toCoin(): Coin{
    return Coin(
        name = name,
        currentPrice = currentPrice,
        imageUrl = image,
        dailyPriceChange = priceChange24h,
        dailyPercentageChange = priceChangePercentage24hInCurrency,
        symbol = symbol,
        marketCap = marketCap,
        sparkLineData = sparklineIn7d.price.takeLast(24)
    )
}