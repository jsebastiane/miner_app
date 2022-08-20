package sebastian.company.min3rapp.domain.model

import android.graphics.drawable.Drawable

data class DiscoverItem(
    val brandName: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val tags: List<String> = listOf(),
    val websiteUrl: String = "",
    val local: Boolean = false
)
