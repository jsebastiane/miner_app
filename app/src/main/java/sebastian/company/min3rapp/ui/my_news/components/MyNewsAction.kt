package sebastian.company.min3rapp.ui.my_news.components

import sebastian.company.min3rapp.domain.model.Article
import sebastian.company.min3rapp.domain.model.DataArticle

interface MyNewsAction {
    fun onClick(article: DataArticle)
}