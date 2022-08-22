package sebastian.company.min3rapp.ui.discuss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.domain.model.discuss.ForumArticle
import sebastian.company.min3rapp.ui.ViewPagerFragmentDirections

@AndroidEntryPoint
class DiscussFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply{
            setContent{
                DiscussPage(navigateToArticleForum = {forumArticle ->
                    navigateToDiscussDetail(forumArticle)

                })
            }
        }

    }

    //will take docId to
    private fun navigateToDiscussDetail(forumArticle: ForumArticle){

        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToDiscussDetailFragment(
            forumArticle.articleId, forumArticle.article.title!!, forumArticle.article.urlToImage!!,
        forumArticle.article.url!!)
        findNavController().navigate(action)
    }


}