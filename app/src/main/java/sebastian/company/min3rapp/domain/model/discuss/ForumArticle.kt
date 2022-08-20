package sebastian.company.min3rapp.domain.model.discuss

import sebastian.company.min3rapp.domain.model.Article

data class ForumArticle(
    val article: Article = Article(),
    val views: Int = 0,
    val commentsCount: Int = 0,
    val articleId: String = ""
//    val createdAt: Timestamp
)
