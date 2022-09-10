package sebastian.company.min3rapp.domain.model.delete_asap

import com.google.android.gms.ads.nativead.NativeAd
import sebastian.company.min3rapp.data.remote.dto.articles.SourceDto

data class DataArticle(
    val publishedAt: String? = null,
    val source: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val nativeAd: NativeAd? = null
)
