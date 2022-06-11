package sebastian.company.min3rapp.ui.news_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.FragmentNewsDetailBinding


class NewsDetailFragment : Fragment() {
    //We need story, imageURL, timePublished,

    val args: NewsDetailFragmentArgs by navArgs()
    private lateinit var imageURL: String
    private lateinit var story: String


    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        if (args.story.isNotEmpty()){
            binding.newsStory.text = args.story
        }

        if (args.title.isNotEmpty()){
            binding.articleHeadline.text = args.title
        }

        if (args.imageUrl.isNotEmpty()){
            Glide.with(this)
                .load(args.imageUrl)
                .into(binding.newsImage)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}