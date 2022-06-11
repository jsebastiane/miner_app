package sebastian.company.min3rapp.ui.news_details

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.FragmentNewsWebBinding

@AndroidEntryPoint
class NewsWebFragment : Fragment() {

    private val args: NewsWebFragmentArgs by navArgs()


    private var _binding: FragmentNewsWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsWebBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsWebView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }
        }

        binding.newsWebView.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.progress = newProgress
            }
        }


        binding.newsWebView.progress
        binding.newsWebView.apply {
            loadUrl(args.newsUrl)
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
        }
    }




}