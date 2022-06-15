package sebastian.company.min3rapp.ui.miner_home

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.FragmentHomeBinding
import sebastian.company.min3rapp.domain.model.Article
import sebastian.company.min3rapp.domain.model.DataArticle
import sebastian.company.min3rapp.ui.ViewPagerFragmentDirections
import sebastian.company.min3rapp.ui.miner_home.components.NewsAction

@AndroidEntryPoint
class HomeFragment : Fragment(), NewsAction {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MinerHomeViewModel by viewModels()
    private var newsAdapter = NewsRecyclerAdapter(this)
    private lateinit var adLoader: AdLoader


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdLoader()
        adLoader.loadAds(AdRequest.Builder().build(), 3)

        observeViewModel()

        binding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.getArticlesCf()

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getArticlesCf()
        }

    }

    private fun observeViewModel(){
        viewModel.requestState.observe(viewLifecycleOwner, Observer { request ->
            request?.let {
                binding.refreshLayout.isRefreshing = false
                if(it.isLoading){
                    binding.homeProgressBar.visibility = View.VISIBLE
                }else{
                    binding.homeProgressBar.visibility = View.GONE
                }

//                if(it.error.isNotEmpty()){
//                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
//                }
                if (it.error.isEmpty()){
                    if(it.articles.isNotEmpty()){
                        newsAdapter.setData(it.articles)
                    }
                }else{
                    Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun createAdLoader() {
        adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-1348069125113037/5204101273")
            .forNativeAd { ad: NativeAd ->
                if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    ad.destroy()
                    return@forNativeAd
                }
                if (activity!!.isDestroyed) {
                    ad.destroy()
                    return@forNativeAd
                } else {
                    //add each ad to a list of ads in viewModel
                    viewModel.setAds(ad)
                    println(ad.body)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(article: DataArticle) {
        if(article.url != null){
            goToNewsWindow(article.url)
        }else{
            Toast.makeText(context, "Error getting article", Toast.LENGTH_SHORT).show()
        }
    }


    private fun goToNewsWindow(newsUrl: String){
        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToNewsWebFragment(newsUrl)
        Navigation.findNavController(binding.root).navigate(action)
    }
}