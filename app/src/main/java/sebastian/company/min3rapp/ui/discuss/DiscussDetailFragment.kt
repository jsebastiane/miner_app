package sebastian.company.min3rapp.ui.discuss

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.domain.model.discuss.ForumArticle
import sebastian.company.min3rapp.domain.model.discuss.ForumComment


class DiscussDetailFragment : Fragment() {

    private var articleIdFrag: String? = null
    private var articleTitle: String? = null
    private var articleImageUrl: String? = null
    private var articleUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            articleIdFrag = DiscussDetailFragmentArgs.fromBundle(it).articleId
            articleTitle = DiscussDetailFragmentArgs.fromBundle(it).articleTitle
            articleImageUrl = DiscussDetailFragmentArgs.fromBundle(it).imageUrl
            articleUrl = DiscussDetailFragmentArgs.fromBundle(it).articleUrl
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                DiscussDetailsMain(articleId = articleIdFrag!!, articleTitle = articleTitle!!,
                    imageUrl = articleImageUrl!!,
                    navigateToComment = {comment, articleId ->
                        navigateToComment(comment, articleId)
                    },
                viewArticle = {navigateToWebView()})
            }
        }
    }

    private fun navigateToComment(comment: ForumComment, articleId: String){
        val action = DiscussDetailFragmentDirections.actionDiscussDetailFragmentToCommentDetailsFragment(comment.commentId,
        comment.text, comment.poster, articleId)
//        Navigation.findNavController(requireView()).navigate(action)
        findNavController().navigate(action)
    }

    private fun navigateToWebView(){
        val action = DiscussDetailFragmentDirections.actionDiscussDetailFragmentToNewsWebFragment(articleUrl!!)
        findNavController().navigate(action)
    }



}