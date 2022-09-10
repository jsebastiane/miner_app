package sebastian.company.min3rapp.ui.my_news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.*
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.databinding.FragmentMyNewsBinding
import sebastian.company.min3rapp.domain.model.delete_asap.DataArticle
import sebastian.company.min3rapp.ui.ViewPagerFragmentDirections
import sebastian.company.min3rapp.ui.my_news.components.MyNewsAction

@AndroidEntryPoint
class MyNewsFragment : Fragment(), MyNewsAction {

    private var _binding: FragmentMyNewsBinding? = null
    private val binding get() = _binding!!
    private var newsBannerRecyclerAdapter = NewsBannerRecyclerAdapter(this)
    private val viewModel: MyNewsViewModel by viewModels()
    private lateinit var adLoader: AdLoader
    private var myTopics: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 2)
        createAdLoader()
//        adLoader.loadAds(AdRequest.Builder().build(), 3)
        observeViewModel()

        binding.articleBannersRecycler.apply {
            adapter = newsBannerRecyclerAdapter
            layoutManager = gridLayoutManager
        }


        binding.addTopicsButton.setOnClickListener {
            val action = ViewPagerFragmentDirections.actionViewPagerFragmentToNewsTagsFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }

        binding.tagsChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds[0])
//            if(chip.isChecked){
//                if(chip.text == "ALL"){
//                    viewModel.getArticlesCf(myTopics)
//                }else{
//                    viewModel.getArticlesCf(chip.text.toString())
//                }
//            }

        }

        binding.refreshLayout.setOnRefreshListener {
            val chipId = binding.tagsChipGroup.checkedChipId
            val checkedChip = binding.tagsChipGroup.findViewById<Chip>(chipId)
            viewModel.getArticlesCf(checkedChip.text.toString())
        }


        //Add swipe to refresh - get chip selected and refresh


        //When position equals firstItem(0), newsItem(1), NewsBanner(2), or adsBanner(3) - adjust
        //the gridSpan size accordingly
        //Grid is initialized with spanCount of 2
        //CarouselView takes up both spaces, the second row news items take 1 each, and the banners
        //occupy 2
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (newsBannerRecyclerAdapter.getItemViewType(position)) {
                    newsBannerRecyclerAdapter.firstItem -> 2
                    newsBannerRecyclerAdapter.newsItem -> 1
                    newsBannerRecyclerAdapter.newsBanner -> 2
                    else -> 2
                }
            }
        }
    }


    private fun observeViewModel() {

        //If no topics are found show option to add, otherwise start loading articles with topics
        viewModel.myTopics.observe(viewLifecycleOwner, Observer { topics ->
            if (topics.isNullOrEmpty()) {
                binding.refreshLayout.visibility = View.GONE
                binding.chipScrollView.visibility = View.GONE
                binding.addTopicsLayout.visibility = View.VISIBLE

            } else {
                binding.chipScrollView.visibility = View.VISIBLE
                binding.refreshLayout.visibility = View.VISIBLE
                binding.addTopicsLayout.visibility = View.GONE
                myTopics = topics
                createNewsTags()
            }
        })

        //change visibility of progress bar and set dataDto for recyclerview
        //Recyclerview disappears as new DataListState containing Loading=true has an empty list of
        //articles
        viewModel.requestState.observe(viewLifecycleOwner, Observer { request ->
            request?.let {
                binding.refreshLayout.isRefreshing = false

                if(it.isLoading){
                    binding.myNewsProgressBar.visibility = View.VISIBLE
                }else{
                    binding.myNewsProgressBar.visibility = View.GONE
                }
                //wait for list of articles with ads from ViewModel
                if (it.error.isEmpty()) {
                    if(it.articles.isNotEmpty()){
                        newsBannerRecyclerAdapter.setData(it.articles)
                        binding.articleBannersRecycler.scrollToPosition(0)
                    }
                } else {
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }


    //Load ads and send to ViewModel
//ca-app-pub-3940256099942544/2247696110
//
    private fun createAdLoader() {
        adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-1348069125113037/5204101273")
            .forNativeAd { ad: NativeAd ->
                if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    ad.destroy()
                    return@forNativeAd
                }
                if (requireActivity().isDestroyed) {
                    ad.destroy()
                    return@forNativeAd
                } else {
                    //add each ad to a list of ads in viewModel
                    viewModel.setAds(ad)
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    //Will add something
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            )
            .build()
    }

    private fun createNewsTags(){
        val listNewsTags = myTopics.split(",").toMutableList()
        listNewsTags.add(0, "ALL")
        binding.tagsChipGroup.removeAllViews()
        viewLifecycleOwner.lifecycleScope.launch{
            println(Thread.currentThread().name)
            for (i in listNewsTags){
                val chip = Chip(context)
                chip.text = i
                binding.tagsChipGroup.addView(chip)
            }
            val allChip = binding.tagsChipGroup.getChildAt(0) as Chip
            allChip.isChecked = true

        }
    }



    override fun onClick(article: DataArticle) {
        if (article.url != null) {
            goToNewsWindow(article.url)
        } else {
            Toast.makeText(context, "Error getting article", Toast.LENGTH_SHORT).show()
        }
    }


    private fun goToNewsWindow(newsUrl: String) {
        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToNewsWebFragment(newsUrl)
        Navigation.findNavController(binding.root).navigate(action)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}