package sebastian.company.min3rapp.ui.my_news.components

import sebastian.company.min3rapp.domain.model.delete_asap.DataArticle

interface MyNewsAction {
    fun onClick(article: DataArticle)
}