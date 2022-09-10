package sebastian.company.min3rapp.ui.miner_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.ads.nativead.NativeAd
import sebastian.company.min3rapp.R

import sebastian.company.min3rapp.databinding.HomePageAdsBinding
import sebastian.company.min3rapp.databinding.NewsItemBinding
import sebastian.company.min3rapp.domain.model.Article
import sebastian.company.min3rapp.ui.miner_home.components.ArticleDiffUtil
import sebastian.company.min3rapp.ui.miner_home.components.NewsAction

class NewsRecyclerAdapter(val actions: NewsAction)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private var oldArticleList = emptyList<Article>()
    private val adItem = 0
    private val newsItem = 1

//    inner class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            adItem -> {
                HomePageAdViewHolder(HomePageAdsBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
            }else ->{
                NewsItemViewHolder(NewsItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
            }
        }
    }

    inner class NewsItemViewHolder(private val newsBinding: NewsItemBinding): RecyclerView
        .ViewHolder(newsBinding.root){
            fun bind(article: Article){
                val newsSource = article.source?: ""
                val sourceAndDate = "$newsSource | ${article.dbAddDate}"
                newsBinding.newsHeadline.text = article.title
                newsBinding.newsSource.text = sourceAndDate

                Glide.with(newsBinding.root)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.img_placeholder)
                    .into(newsBinding.newsImage)

                newsBinding.root.setOnClickListener {
                    actions.onClick(article)
                }
            }
        }

    inner class HomePageAdViewHolder(private val adsBinding: HomePageAdsBinding) : RecyclerView
        .ViewHolder(adsBinding.root){
            fun bind(article: Article){
                displayNativeAd(adsBinding.root, article.nativeAd!!)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = oldArticleList[position]
        when(getItemViewType(position)){
            adItem -> (holder as HomePageAdViewHolder).bind(article)
            else -> (holder as NewsItemViewHolder).bind(article)
        }


    }

    override fun getItemCount(): Int {
        return oldArticleList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return when{
            oldArticleList[position].nativeAd != null ->{
                adItem
            }else -> {
                newsItem
            }
        }
    }

    fun setData(newArticleList: List<Article>){
        val diffUtil = ArticleDiffUtil(oldArticleList, newArticleList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldArticleList = newArticleList
        diffResults.dispatchUpdatesTo(this)
    }

    fun displayNativeAd(parent: ViewGroup, ad: NativeAd) {

//        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
//                as LayoutInflater
        val adView = HomePageAdsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)

        val adMediaView = adView.adMedia
        val headlineView = adView.adHeadline
        val adActionButton = adView.adAction
        val iconView = adView.adAppIcon
        val adBody = adView.adBody
        val adChoices = adView.adChoicesView2

        adView.root.adChoicesView = adChoices


        //If ad has icon do not show Image - if no icon show image
        if(ad.icon == null){
            adView.adAppIcon.visibility = View.GONE
            if(ad.mediaContent == null){
                adMediaView.visibility = View.GONE
            }else{
                adView.root.mediaView = adMediaView
                adView.root.mediaView!!.setMediaContent(ad.mediaContent!!)
                adView.root.mediaView!!.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                adMediaView.visibility = View.VISIBLE
            }
        }else{
            adMediaView.visibility = View.GONE
            iconView.setImageDrawable(ad.icon!!.drawable)
            adView.root.iconView = iconView
            adView.adAppIcon.visibility = View.VISIBLE
        }

        if(ad.headline == null){
            headlineView.text = ""
        }else{
            headlineView.text = ad.headline
            adView.root.headlineView = headlineView
        }

        //instructions say there is always a call to action
        if(ad.callToAction == null){
            val placeHolderTxt = "Get it"
            adActionButton.text = placeHolderTxt
        }else{
            adActionButton.text = ad.callToAction
            adView.root.callToActionView = adActionButton
        }

        if(ad.body == null){
            adBody.text = ""
        }else{
            adBody.text = ad.body
            adView.root.bodyView = adBody
        }



        adView.root.setNativeAd(ad)

        parent.removeAllViews()
        parent.addView(adView.root)
    }


}