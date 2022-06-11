package sebastian.company.min3rapp.domain.model

data class Coin(
    val name: String? = null,
    val currentPrice: Double? = null,
    val imageUrl: String? = null,
    val dailyPriceChange: Double? = null,
    val dailyPercentageChange: Double? = null,
    val symbol: String? = null,
    val marketCap: Long? = null,
    val sparkLineData: List<Double>? = null
)