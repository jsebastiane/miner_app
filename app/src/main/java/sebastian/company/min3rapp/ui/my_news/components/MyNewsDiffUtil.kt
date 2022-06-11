package sebastian.company.min3rapp.ui.my_news.components

import androidx.recyclerview.widget.DiffUtil
import sebastian.company.min3rapp.domain.model.DataArticle

class MyNewsDiffUtil(
    private val oldList: List<DataArticle>,
    private val newList: List<DataArticle>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.count()
    }

    override fun getNewListSize(): Int {
        return newList.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].url == newList[newItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}