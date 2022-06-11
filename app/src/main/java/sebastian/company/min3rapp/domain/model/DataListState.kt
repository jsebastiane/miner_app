package sebastian.company.min3rapp.domain.model

data class DataListState(
    val isLoading: Boolean = false,
    val articles: List<DataArticle> = emptyList(),
    val error: String = "",
)
