package sebastian.company.min3rapp.ui.my_news.news_tags

import androidx.recyclerview.widget.DiffUtil
import sebastian.company.min3rapp.domain.model.NewsTag

class NewsTagDiffUtil(
    private val oldList: List<NewsTag>,
    private val newList: List<NewsTag>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.count()
    }

    override fun getNewListSize(): Int {
        return newList.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].tagIsChecked == newList[newItemPosition].tagIsChecked
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}