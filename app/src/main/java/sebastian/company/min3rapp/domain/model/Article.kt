package sebastian.company.min3rapp.domain.model

import com.google.android.gms.ads.nativead.NativeAd
import sebastian.company.min3rapp.data.remote.dto.articles.SourceDto


data class Article (
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val sourceDto: SourceDto? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val nativeAd: NativeAd? = null

    )