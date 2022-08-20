package sebastian.company.min3rapp.ui.discuss.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import sebastian.company.min3rapp.common.ForumDataType
import sebastian.company.min3rapp.domain.model.discuss.*

class ForumViewModel(private val dataType: ForumDataType,
                     private val articleId: String?,
                     private val commendId: String?): ViewModel() {

    private val firestoreDb = FirebaseFirestore.getInstance()
    private val forumPath = firestoreDb.collection("forumTopics")

    var addCommentState by mutableStateOf<AddCommentState>(AddCommentState())
        private set

    var forumArticlesState by mutableStateOf<ForumArticleState>(ForumArticleState())
        private set

    var forumCommentsState by mutableStateOf<ForumCommentsState>(ForumCommentsState())
        private set

    var forumRepliesState by mutableStateOf<ForumCommentsState>(ForumCommentsState())
        private set


    init {
        when(dataType){
            ForumDataType.FORUM_TOPICS -> getForumTopics()
            ForumDataType.FORUM_COMMENT -> getForumComments()
            else -> getForumNestedReplies()
        }
    }



    private fun getForumTopics(){
        forumArticlesState = ForumArticleState(loading = true)
        val tempList = mutableListOf<ForumArticle>()
        forumPath.get()
            .addOnSuccessListener { documents ->
                for(doc in documents){
                    val topic = doc.toObject<ForumArticle>()
                    tempList.add(topic)
                }

                Log.d("Articles", "Got Articles")
                forumArticlesState = ForumArticleState(forumArticles = tempList)
            }
            .addOnFailureListener {
                forumArticlesState = ForumArticleState(error = "Error retrieving forum topics")
            }
    }


    private fun getForumComments(){
        forumCommentsState = ForumCommentsState(loading = true)
        val tempList = mutableListOf<ForumComment>()
        articleId?.let {docId ->
            forumPath.document(docId).collection("comments")
                .get()
                .addOnSuccessListener { documents ->
                    for(doc in documents){
                        val comment = doc.toObject<ForumComment>()
                        tempList.add(comment)
                    }
                    forumCommentsState = ForumCommentsState(forumComments = tempList)

                }
        }

    }

    private fun getForumNestedReplies(){
        forumRepliesState = ForumCommentsState(loading = true)
        val tempList = mutableListOf<ForumComment>()
        articleId?.let { docId ->
            commendId?.let { commId ->
                forumPath.document(docId)
                    .collection("comments")
                    .document(commId)
                    .collection("nestedReplies")
                    .get()
                    .addOnSuccessListener { documents ->
                        for(doc in documents){
                            val reply = doc.toObject<ForumComment>()
                            tempList.add(reply)
                        }
                        forumRepliesState = ForumCommentsState(forumComments = tempList)
                    }
                    .addOnFailureListener {
                        forumRepliesState = ForumCommentsState(error = "Failed to load comments")
                    }
            }
        }
    }

    fun addComment(comment: String){
        addCommentState = AddCommentState(loading = true)
        articleId?.let{ docId ->
            val newComment = forumPath
                .document(docId)
                .collection("comments")
                .document()

            newComment.set(ForumComment(commentId = newComment.id, comment))
                .addOnSuccessListener {
                    addCommentState = AddCommentState(success = true)
                    getForumComments()
                }
                .addOnFailureListener {
                    addCommentState =  AddCommentState(failed = true)}

        }

    }

    private fun addCommentReply(){

    }

}