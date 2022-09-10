package sebastian.company.min3rapp.ui.miner_home.components

import androidx.recyclerview.widget.DiffUtil
import sebastian.company.min3rapp.domain.model.Article

class ArticleDiffUtil(
    private val oldList: List<Article>,
    private val newList: List<Article>
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