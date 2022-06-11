package sebastian.company.min3rapp.domain.model

import sebastian.company.min3rapp.domain.model.Article

data class ArticlesListState (
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String = ""
        )