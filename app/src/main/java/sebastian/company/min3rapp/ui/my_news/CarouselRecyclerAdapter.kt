package sebastian.company.min3rapp.ui.my_news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sebastian.company.min3rapp.databinding.ArticleCarouselItemBinding
import sebastian.company.min3rapp.domain.model.Article

class CarouselRecyclerAdapter: RecyclerView.Adapter<CarouselRecyclerAdapter.CarouselViewHolder>() {

    private var oldArticleList = emptyList<Article>()

    inner class CarouselViewHolder(val binding: ArticleCarouselItemBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        return CarouselViewHolder(ArticleCarouselItemBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val article = oldArticleList[position]
        holder.binding.newsHeadline.text = article.title
        holder.binding.timeSincePosted.text = article.dbAddDate?.toDate().toString()
        Glide.with(holder.binding.root)
            .load(article.urlToImage)
            .into(holder.binding.newsImage)
    }

    override fun getItemCount(): Int {
        return oldArticleList.count()
    }


    fun setData(newArticleList: List<Article>){
        oldArticleList = newArticleList
        notifyDataSetChanged()
    }


}