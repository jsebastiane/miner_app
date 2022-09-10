package sebastian.company.min3rapp.domain.model

import com.google.android.gms.ads.nativead.NativeAd
import com.google.firebase.Timestamp
import sebastian.company.min3rapp.data.remote.dto.articles.SourceDto


data class Article (
    val title: String? = null,
    val source: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val type: String? = null,
    var dbAddDate: Timestamp? = null,
    val added: Boolean = false,
    val nativeAd: NativeAd? = null

    )