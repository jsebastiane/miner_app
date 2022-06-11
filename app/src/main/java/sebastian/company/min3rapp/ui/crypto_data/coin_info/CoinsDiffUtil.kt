package sebastian.company.min3rapp.ui.crypto_data.coin_info

import androidx.recyclerview.widget.DiffUtil
import sebastian.company.min3rapp.domain.model.Coin

class CoinsDiffUtil(
    private val oldList: List<Coin>,
    private val newList: List<Coin>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.count()
    }

    override fun getNewListSize(): Int {
        return newList.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].symbol == newList[newItemPosition].symbol
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}