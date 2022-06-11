package sebastian.company.min3rapp.data.remote.dto.articles

import com.google.gson.annotations.SerializedName

data class ArticlesDto(
    @SerializedName("articles")
    val articleDtos: List<ArticleDto>?,
    val status: String?,
    val totalResults: Int?
)

