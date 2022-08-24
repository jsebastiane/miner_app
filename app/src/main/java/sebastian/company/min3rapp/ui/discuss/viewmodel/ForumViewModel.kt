package sebastian.company.min3rapp.ui.discuss.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import sebastian.company.min3rapp.common.ForumDataType
import sebastian.company.min3rapp.domain.model.User
import sebastian.company.min3rapp.domain.model.discuss.*

class ForumViewModel(private val dataType: ForumDataType,
                     private val articleId: String?,
                     private val commendId: String?): ViewModel() {

    private val firestoreDb = FirebaseFirestore.getInstance()
    private val forumPath = firestoreDb.collection("forumTopics")

    private val currentUser: String? = Firebase.auth.currentUser?.uid


    var addCommentState by mutableStateOf<AddCommentState>(AddCommentState())
        private set

    var voteState by mutableStateOf<VoteState>(VoteState())
        private set

    var forumArticlesState by mutableStateOf<ForumArticleState>(ForumArticleState())
        private set

    var forumCommentsState by mutableStateOf<ForumCommentsState>(ForumCommentsState())
        private set

    var forumRepliesState by mutableStateOf<ForumCommentsState>(ForumCommentsState())
        private set

    private var user: User? = null



    init {
        getUser()
        when(dataType){
            ForumDataType.FORUM_TOPICS -> getForumTopics()
            ForumDataType.FORUM_COMMENT -> getForumComments()
            else -> getForumNestedReplies()
        }
    }

    private fun getUser(){
        currentUser?.let { id ->
            firestoreDb.collection("users").document(id).get()
                .addOnSuccessListener { document ->
                    user = document.toObject<User>()
                }.addOnFailureListener {
                    Log.d("UserRetrieval", "Failed")
                }

        }
        if(currentUser == null){
            Log.d("UserData", "No user found")
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
        forumCommentsState = ForumCommentsState(loading = true, forumComments = forumCommentsState.forumComments)
        val tempList = mutableListOf<ForumComment>()
        articleId?.let {docId ->
            forumPath.document(docId).collection("comments")
                .get()
                .addOnSuccessListener { documents ->
                    for(doc in documents){
                        val comment = doc.toObject<ForumComment>()
                        tempList.add(comment)
                    }

                    checkCommentLikes(tempList)

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

    //These comments must also be added to the Users comments collection
    fun addComment(comment: String){
        addCommentState = AddCommentState(loading = true)
        articleId?.let{ docId ->
            val newComment = forumPath
                .document(docId)
                .collection("comments")
                .document()

            newComment.set(ForumComment(commentId = newComment.id, text = comment, timePosted = Timestamp.now()))
                .addOnSuccessListener {
                    addCommentState = AddCommentState(success = true)
                    getForumComments()
                }
                .addOnFailureListener {
                    addCommentState =  AddCommentState(failed = true)}

        }

    }

    fun addReply(comment: String){
        addCommentState = AddCommentState(loading = true)
        articleId?.let { docId ->
            commendId?.let { commId ->
                val newComment = forumPath.document(docId)
                    .collection("comments")
                    .document(commId)
                    .collection("nestedReplies")
                    .document()

                newComment.set(ForumComment(poster = currentUser!!,
                    commentId = commId,
                    text = comment,
                    timePosted = Timestamp.now()))
                    .addOnSuccessListener {
                        addCommentState = AddCommentState(success = true)
                        getForumNestedReplies()
                    }.addOnFailureListener {
                        addCommentState = AddCommentState(failed = true)
                    }

            }
        }

    }


    //************************ DO NOT UPDATE COMMENTS LIST AFTER VOTE *************************

    //vote is current reaction to certain comment (1,0,-1)
    //shardVote is the absolute effect of downvoting or upvoting which can be (2,1,0,-1,-2)
    fun commentVote(key: String, vote: Int, shardVote: Int){
        //Random shard allocator cod here!
        //Check size of currentReactionsMap -> can't exceed 4000
        if(currentUser != null && articleId != null){
            val userDB = firestoreDb.collection("users").document(currentUser)
//            val randomShard = FirestoreDb.collection("forumTopics")
//                .document(articleId).collection("votes").document("vote$")

            when(vote){
                0 -> {
                    userDB.set(mapOf("currentReactionsMap" to mapOf(key to FieldValue.delete())), SetOptions.merge())
                        .addOnFailureListener {
                            Log.d("UserReaction", "Failed")
                        }
                    }
                else -> {
                    userDB.set(mapOf("currentReactionsMap" to mapOf(key to vote)), SetOptions.merge())
                        .addOnFailureListener {
                            Log.d("UserReaction", "Failed")
                        }
                    }
                }

            //shard.increment(shardVote)
            println(shardVote.toString())

        }else{
            voteState = VoteState(success = false, error = "Failed to send vote")

        }
    }


    private fun sortForumComments(sortType: Int){
        when(sortType){
           0 -> {}
        }
    }

    //maybe run in a coroutine?
    private fun checkCommentLikes(comments: List<ForumComment>){
        val tempList = comments
        if (user != null){
            for(reaction in user!!.currentReactionsMap){
                tempList.find { it.commentId == reaction.key }?.userReaction = reaction.value
            }
        }
        forumCommentsState = ForumCommentsState(forumComments = tempList)


    }

}