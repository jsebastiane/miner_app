package sebastian.company.min3rapp.domain.model.delete_asap

import sebastian.company.min3rapp.domain.model.delete_asap.DataArticle

data class DataListState(
    val isLoading: Boolean = false,
    val articles: List<DataArticle> = emptyList(),
    val error: String = "",
)
