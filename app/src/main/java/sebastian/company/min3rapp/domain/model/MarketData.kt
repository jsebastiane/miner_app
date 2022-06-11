package sebastian.company.min3rapp.domain.model

data class MarketData(
    val marketCapChange24h: Double? = null,
    val marketShareMap: List<CoinMarketShare>? = null
)
