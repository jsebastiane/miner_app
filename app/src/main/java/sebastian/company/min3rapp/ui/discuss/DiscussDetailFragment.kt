package sebastian.company.min3rapp.ui.discuss

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.domain.model.discuss.ForumArticle
import sebastian.company.min3rapp.domain.model.discuss.ForumComment


class DiscussDetailFragment : Fragment() {

    private var articleIdFrag: String? = null
    private var articleTitle: String? = null
    private var articleImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            articleIdFrag = DiscussDetailFragmentArgs.fromBundle(it).articleId
            articleTitle = DiscussDetailFragmentArgs.fromBundle(it).articleTitle
            articleImageUrl = DiscussDetailFragmentArgs.fromBundle(it).imageUrl
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
                    navigateToComment = {comment ->
                        navigateToComment(comment)
                    })
            }
        }
    }

    private fun navigateToComment(comment: ForumComment){
        val action = DiscussDetailFragmentDirections.actionDiscussDetailFragmentToCommentDetailsFragment(comment.commentId)
        findNavController().navigate(action)
    }



}