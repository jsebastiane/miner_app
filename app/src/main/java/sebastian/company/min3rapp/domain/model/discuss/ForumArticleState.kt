package sebastian.company.min3rapp.domain.model.discuss

data class ForumArticleState(
    val forumArticles: List<ForumArticle>? = null,
    var loading: Boolean? = null,
    val error: String? = null
)
