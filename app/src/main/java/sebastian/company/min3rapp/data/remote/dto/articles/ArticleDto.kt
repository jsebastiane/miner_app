package sebastian.company.min3rapp.data.remote.dto.articles

import android.annotation.SuppressLint
import sebastian.company.min3rapp.domain.model.Article
import java.text.SimpleDateFormat


data class ArticleDto(
    val author: String? = null,
//    val content: String? = null,
//    val description: String? = null,
    val publishedAt: String? = null,
    val source: SourceDto? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

@SuppressLint("SimpleDateFormat")
fun ArticleDto.toArticle(): Article {

    val dateTemplate = "MMM d"
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val formatter = SimpleDateFormat(dateTemplate)
    val parsedDate = parser.parse(publishedAt!!)
    val date = formatter.format(parsedDate!!)


    return Article(
        author = author,
        publishedAt = date,
        sourceDto = source,
        title = title,
        url = url,
        urlToImage = urlToImage
            )
}