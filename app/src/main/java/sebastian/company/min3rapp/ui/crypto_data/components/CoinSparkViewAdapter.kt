package sebastian.company.min3rapp.ui.crypto_data.components

import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.SparkView

class CoinSparkViewAdapter(private val coinPricePoints: ArrayList<Double>) : SparkAdapter() {

    fun setData(newCoinPricePoints: List<Double>){
        coinPricePoints.clear()
        coinPricePoints.addAll(newCoinPricePoints)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return coinPricePoints.count()
    }

    override fun getItem(index: Int): Any {
        return coinPricePoints[index]
    }

    override fun getY(index: Int): Float {
        return coinPricePoints[index].toFloat()
    }
}