package sebastian.company.min3rapp.domain.model

data class DiscoverItemState(
    val isLoading: Boolean = false,
    val discoverItems: List<DiscoverItem> = emptyList(),
    val error: String = ""

)