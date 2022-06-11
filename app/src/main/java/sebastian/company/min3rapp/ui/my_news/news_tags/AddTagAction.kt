package sebastian.company.min3rapp.ui.my_news.news_tags

import sebastian.company.min3rapp.domain.model.NewsTag

interface AddTagAction {
    fun onAddRemoveTag(newsTag: NewsTag)
}