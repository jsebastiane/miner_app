package sebastian.company.min3rapp.ui.miner_home.components

import sebastian.company.min3rapp.domain.model.Article
import sebastian.company.min3rapp.domain.model.DataArticle

interface NewsAction {
    fun onClick(article: DataArticle)
}