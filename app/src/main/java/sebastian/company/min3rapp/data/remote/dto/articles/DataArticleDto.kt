package sebastian.company.min3rapp.data.remote.dto.articles

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import sebastian.company.min3rapp.domain.model.delete_asap.DataArticle
import java.text.SimpleDateFormat

data class DataArticleDto(
    @SerializedName("source_name")
    val source: String? = null,
    @SerializedName("date")
    val publishedAt: String? = null,
    @SerializedName("news_url")
    val url: String? = null,
    @SerializedName("image_url")
    val urlToImage: String? = null,

    val title: String? = null
)

@SuppressLint("SimpleDateFormat")
fun DataArticleDto.toDataArticle(): DataArticle {
    val dateTemplate = "MMMM d"
    val parser = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
    val formatter = SimpleDateFormat(dateTemplate)
    val parsedDate = parser.parse(publishedAt!!)
    val date = formatter.format(parsedDate!!)

    return DataArticle(
        publishedAt = date,
        source = source,
        url = url,
        urlToImage = urlToImage,
        title = title
    )
}
