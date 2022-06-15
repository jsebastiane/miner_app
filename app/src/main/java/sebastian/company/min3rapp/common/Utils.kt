package sebastian.company.min3rapp.common

import android.content.Context
import android.graphics.ColorFilter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import sebastian.company.min3rapp.R
import java.text.DecimalFormat

fun formatPrice(price: Double) : String{

    var decFormat = DecimalFormat("")
    val dec1 = DecimalFormat("$###,###.##")
    val dec2 = DecimalFormat("$#.#####")
    val dec3 = DecimalFormat("$#.########")


    if(price >= 1){
        decFormat = dec1
    }else if(price < 1 && price >= 0.0001){
        decFormat = dec2
    }else if(price < 0.0001){
        decFormat = dec3
    }

    return decFormat.format(price)

}

fun formatMktCap(marketCap: Long) : String{

    var newMarketCap = ""

    val trilFormat = DecimalFormat("$###.##T")
    val bilFormat = DecimalFormat("$###.##B")

    if(marketCap >= 1000000000000) {
        val tempFigure = marketCap / 1000000000000
        newMarketCap = trilFormat.format(tempFigure)

    } else if (marketCap <= 999999999999){
        val tempFigure = marketCap / 1000000000
        newMarketCap = bilFormat.format(tempFigure)
    }

    return newMarketCap
}

fun Double.formatPercentage() = "%.2f%%".format(this)





