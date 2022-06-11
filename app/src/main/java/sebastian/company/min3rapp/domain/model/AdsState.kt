package sebastian.company.min3rapp.domain.model

import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

data class AdsState(
    val ads: ArrayList<NativeAd> = arrayListOf(),
    val error: LoadAdError? = null
// will add a loading variable when i think about its relevance in the context of my code
)