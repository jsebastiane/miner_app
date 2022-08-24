package sebastian.company.min3rapp.ui.discuss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.domain.model.discuss.ForumComment


class CommentDetailsFragment : Fragment() {

    private var comment: String? = null
    private var commentId: String? = null
    private var commentPoster: String? = null
    private var articleDocId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            comment = CommentDetailsFragmentArgs.fromBundle(it).focusedCommentText
            commentId = CommentDetailsFragmentArgs.fromBundle(it).focusedComment
            commentPoster = CommentDetailsFragmentArgs.fromBundle(it).focusedCommentPoster?: ""
            articleDocId = CommentDetailsFragmentArgs.fromBundle(it).articleId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                CommentDetailsMain(ForumComment("commentPoster", commentId = commentId!!,
                text = comment!!), articleDocId!!)
            }
        }
    }

}