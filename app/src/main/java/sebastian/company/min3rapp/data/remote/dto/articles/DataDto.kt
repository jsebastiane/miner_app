package sebastian.company.min3rapp.data.remote.dto.articles

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("data")
    val articles: List<DataArticleDto>?
)
