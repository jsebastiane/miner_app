package sebastian.company.min3rapp.ui.crypto_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sebastian.company.min3rapp.common.formatPercentage
import sebastian.company.min3rapp.databinding.MarketShareLayoutBinding
import sebastian.company.min3rapp.domain.model.CoinMarketShare
import sebastian.company.min3rapp.domain.model.NewsTag

class MarketShareAdapter : RecyclerView.Adapter<MarketShareAdapter.MarketShareViewHolder>() {

    private var oldCoins = emptyList<CoinMarketShare>()


    inner class MarketShareViewHolder(val binding: MarketShareLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketShareViewHolder {
        return MarketShareViewHolder(
            MarketShareLayoutBinding.inflate(LayoutInflater.from(parent.context),
        parent,
        false))
    }

    override fun onBindViewHolder(holder: MarketShareViewHolder, position: Int) {
        val coin = oldCoins[position]
        holder.binding.assetName.text = coin.coinName.uppercase()
        holder.binding.assetMarketShare.text = coin.marketShare.formatPercentage()
        holder.binding.marketShareProgress.progress = coin.marketShare.toInt()

    }

    override fun getItemCount(): Int {
        return oldCoins.count()
    }

    fun setData(newCoins: List<CoinMarketShare>){
//        val diffUtil = NewsTagDiffUtil(oldNewsTags, newNewsTags)
//        val diffResults = DiffUtil.calculateDiff(diffUtil)
//        Log.d("DiffUtil", diffResults.toString())
        oldCoins = newCoins
        notifyDataSetChanged()
//        diffResults.dispatchUpdatesTo(this)
    }
}