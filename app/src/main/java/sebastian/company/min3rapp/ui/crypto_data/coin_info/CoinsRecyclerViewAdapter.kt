package sebastian.company.min3rapp.ui.crypto_data.coin_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.NonDisposableHandle.parent
import okhttp3.internal.assertThreadDoesntHoldLock
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.common.formatMktCap
import sebastian.company.min3rapp.common.formatPercentage
import sebastian.company.min3rapp.common.formatPrice
import sebastian.company.min3rapp.databinding.CryptoCoinItemBinding
import sebastian.company.min3rapp.domain.model.Coin
import sebastian.company.min3rapp.ui.crypto_data.components.CoinAction
import sebastian.company.min3rapp.ui.crypto_data.components.CoinSparkViewAdapter
import kotlin.coroutines.coroutineContext

class CoinsRecyclerViewAdapter(val action: CoinAction)
    : RecyclerView.Adapter<CoinsRecyclerViewAdapter.CoinsViewHolder>(){

    private var oldCoinsList = emptyList<Coin>()

    inner class CoinsViewHolder(val binding: CryptoCoinItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsViewHolder {
        return CoinsViewHolder(CryptoCoinItemBinding.inflate(LayoutInflater.from(parent.context),
        parent,
        false))
    }

    override fun onBindViewHolder(holder: CoinsViewHolder, position: Int) {
        //See if there is a better way of accomplishing this
        val sparkViewAdapter = CoinSparkViewAdapter(arrayListOf())
        holder.binding.assetSparkView.apply {
            adapter = sparkViewAdapter
        }

        val coin = oldCoinsList[position]
        holder.binding.assetName.text = coin.name
        holder.binding.assetTicker.text = coin.symbol
        Glide.with(holder.binding.root)
            .load(coin.imageUrl)
            .into(holder.binding.assetIcon)
        coin.currentPrice?.let {
            holder.binding.assetPrice.text = formatPrice(it)
        }
        coin.marketCap?.let {
            val mktCapString = "MCap: ${formatMktCap(it)}"
            holder.binding.assetMarketCap.text = mktCapString
        }
        coin.dailyPercentageChange?.let {
            holder.binding.dailyPercChange.text = it.formatPercentage()
            if(it < 0){
                holder.binding.dailyPercChange.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.red))
                holder.binding.assetSparkView.lineColor = ContextCompat.getColor(holder.binding.root.context, R.color.red)
            }else{
                holder.binding.dailyPercChange.setTextColor(ContextCompat.getColor(holder.binding.root.context, R.color.green))
                holder.binding.assetSparkView.lineColor = ContextCompat.getColor(holder.binding.root.context, R.color.green)
            }
        }

        coin.sparkLineData?.let {
            sparkViewAdapter.setData(it)
        }



    }

    override fun getItemCount(): Int {
        return oldCoinsList.count()
    }

    fun setData(newCoinsList: List<Coin>){
        val diffUtil = CoinsDiffUtil(oldCoinsList, newCoinsList)
        val diffUtilResults = DiffUtil.calculateDiff(diffUtil)
        oldCoinsList = newCoinsList
        diffUtilResults.dispatchUpdatesTo(this)
    }
}