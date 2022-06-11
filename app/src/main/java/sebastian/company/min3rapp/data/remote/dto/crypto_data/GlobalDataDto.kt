package sebastian.company.min3rapp.data.remote.dto.crypto_data

import com.google.gson.annotations.SerializedName

data class GlobalDataDto(
    @SerializedName("data")
    val data: MarketDataDto
)
