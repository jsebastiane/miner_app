package sebastian.company.min3rapp.ui.my_news


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.nativead.NativeAd
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.*
import sebastian.company.min3rapp.domain.model.DataArticle
import sebastian.company.min3rapp.ui.my_news.components.MyNewsAction
import sebastian.company.min3rapp.ui.my_news.components.MyNewsDiffUtil


class NewsBannerRecyclerAdapter(val action: MyNewsAction)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val firstItem = 0
    internal val newsBanner = 1
    internal val newsItem = 2
    private val adBanner = 3

    private var oldArticleList = emptyList<DataArticle>()

//    inner class NewsBannerViewHolder(val binding: ArticleBannerItemBinding) : RecyclerView
//            .ViewHolder(binding.root)


    inner class NewsBannerViewHolder(private val leftBinding: ArticleBannerItemBinding) : RecyclerView
        .ViewHolder(leftBinding.root){
            fun bind(article: DataArticle){
                leftBinding.newsHeadline.text= article.title
                leftBinding.newsSource.text = article.source?: ""
                leftBinding.timeSincePosted.text = article.publishedAt
                leftBinding.root.setOnClickListener {
                    action.onClick(article)
                }
                Glide.with(leftBinding.root)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.img_placeholder)
                    .into(leftBinding.articleImage)
            }

        }

    inner class NewsItemSmallViewHolder(private val rightBinding: NewsItemSmallBinding) : RecyclerView
        .ViewHolder(rightBinding.root){
            fun bind(article: DataArticle){
                rightBinding.newsHeadline.text= article.title
                rightBinding.newsSource.text = article.source?: ""
                rightBinding.timeSincePosted.text = article.publishedAt
                rightBinding.root.setOnClickListener {
                    action.onClick(article)
                }
                Glide.with(rightBinding.root)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.img_placeholder)
                    .into(rightBinding.newsImage)
            }
        }

    inner class CarouselNewsViewHolder(private val carouselBinding: ArticleCarouselItemBinding) : RecyclerView
        .ViewHolder(carouselBinding.root){
            fun bind(article: DataArticle){
                carouselBinding.newsHeadline.text = article.title
                carouselBinding.newsSource.text = article.source?: ""
                carouselBinding.timeSincePosted.text = article.publishedAt
                carouselBinding.root.setOnClickListener {
                    action.onClick(article)
                }
                Glide.with(carouselBinding.root)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.img_placeholder)
                    .into(carouselBinding.newsImage)
            }
        }

    inner class BannerAdViewHolder(private val adBannerBinding: AdBannerBinding) : RecyclerView
        .ViewHolder(adBannerBinding.root){
            fun bind(article: DataArticle){
                displayNativeAd(adBannerBinding.root, article.nativeAd!!)
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            firstItem -> {
                CarouselNewsViewHolder(ArticleCarouselItemBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }
            newsBanner -> {
                NewsBannerViewHolder(ArticleBannerItemBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            }

            adBanner -> {
                BannerAdViewHolder(AdBannerBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))
            }
            else -> {
                NewsItemSmallViewHolder((NewsItemSmallBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)))
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = oldArticleList[position]
        when(getItemViewType(position)){
            firstItem -> (holder as CarouselNewsViewHolder).bind(article)
            newsBanner -> (holder as NewsBannerViewHolder).bind(article)
            adBanner -> (holder as BannerAdViewHolder).bind(article)
            else -> (holder as NewsItemSmallViewHolder).bind(article)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when{
            position == 0 -> {
                firstItem
            }
            position == 1 || position == 2 -> {
                newsItem
            }

            position > 2 && oldArticleList[position].nativeAd != null ->{
                adBanner
            }
            else -> {
                newsBanner
            }
        }
    }


    override fun getItemCount(): Int {
        return oldArticleList.count()
    }

    fun setData(newArticleList: List<DataArticle>){
        val diffUtil = MyNewsDiffUtil(oldArticleList, newArticleList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        oldArticleList = newArticleList
        diffResults.dispatchUpdatesTo(this)
    }

    fun displayNativeAd(parent: ViewGroup, ad: NativeAd) {
        val adView = AdBannerBinding.inflate(LayoutInflater.from(parent.context),
        parent,
        false)


//        NOT USING ICONS AT THE MOMENT
//        val iconView = adView.adAppIcon
        val adMediaView = adView.adMedia
        val adActionButton = adView.adAction
        val headlineView = adView.adHeadline
        val adBody = adView.adBody
        val adChoice = adView.adChoicesView

        adView.root.adChoicesView = adChoice


        if (ad.headline == null){
            headlineView.text = ""
        }else{
            headlineView.text = ad.headline
            adView.root.headlineView = headlineView
        }

        //instructions say there is always an image - we'll see
        if(ad.mediaContent == null){
            adMediaView.visibility = View.GONE
        }else{
            adView.root.mediaView = adMediaView
            adView.root.mediaView!!.setMediaContent(ad.mediaContent!!)
            adView.root.mediaView!!.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            adMediaView.visibility = View.VISIBLE
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

        //        if(ad.icon == null){
//            adView.adAppIcon.visibility = View.GONE
//        }else{
//            iconView.setImageDrawable(ad.icon!!.drawable)
//            adView.root.iconView = iconView
//            adView.adAppIcon.visibility = View.VISIBLE
//        }

        adView.root.setNativeAd(ad)

        parent.removeAllViews()
        parent.addView(adView.root)
    }
}