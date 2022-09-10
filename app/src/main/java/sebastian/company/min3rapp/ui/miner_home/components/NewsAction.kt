package sebastian.company.min3rapp.ui.miner_home.components

import sebastian.company.min3rapp.domain.model.Article

interface NewsAction {
    fun onClick(article: Article)
}