package sebastian.company.min3rapp.ui.discuss.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import sebastian.company.min3rapp.common.ForumDataType
import sebastian.company.min3rapp.domain.model.User
import sebastian.company.min3rapp.domain.model.discuss.*

class ForumViewModel(
    private val dataType: ForumDataType,
    private val articleId: String?,
    private val commendId: String?): ViewModel() {

    private val firestoreDb = FirebaseFirestore.getInstance()
    private val forumPath = firestoreDb.collection("forumTopics")

    private val currentUser: String? = Firebase.auth.currentUser?.uid

    private val shards = 10


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
    }

    private fun getUser(){
        currentUser?.let { id ->
            firestoreDb.collection("users").document(id).get()
                .addOnSuccessListener { document ->
                    user = document.toObject<User>()
                    Log.d("UserRetrieval", "Success")
                }.addOnFailureListener {
                    Log.d("UserRetrieval", "Failed")
                }.addOnCompleteListener {
                    when(dataType){
                        ForumDataType.FORUM_TOPICS -> getForumTopics()
                        ForumDataType.FORUM_COMMENT -> getForumComments()
                        else -> getForumNestedReplies()
                    }
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

                    Log.d("CheckCommentLikes", "Calling check comment likes")
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
                        checkRepliesLikes(tempList)
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




    fun commentVote(key: String, vote: Int, shardVote: Int){
        //Check size of currentReactionsMap -> can't exceed 4000
        Log.d("Voting", "Voting Now")
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

            addVoteToShard(shardVote, key)

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

        if (user != null){
            for(reaction in user!!.currentReactionsMap){
                comments.find { it.commentId == reaction.key }?.userReaction = reaction.value
            }
        }
        forumCommentsState = ForumCommentsState(forumComments = comments)

    }


    private fun checkRepliesLikes(comments: List<ForumComment>){

        if (user != null){
            for(reaction in user!!.currentReactionsMap){
                comments.find { it.commentId == reaction.key }?.userReaction = reaction.value
            }
        }
        forumRepliesState = ForumCommentsState(forumComments = comments)


    }

    //adding vote to a shard that will be read every X minutes by MIN3R's cloud function
    //and aggregated to update the comment's total votes
    //vote is current reaction to certain comment (1,0,-1)
    //shardVote is the absolute effect of downvoting or upvoting which can be (2,1,0,-1,-2)
    private fun addVoteToShard(vote: Int, commentDocId: String){
        val randomShard = (0..shards).random()
        articleId?.let{
            firestoreDb.collection("forumTopics")
                .document(it)
                .collection("comments")
                .document(commentDocId)
                .collection("vote_shards")
                .document("votes$randomShard")
                .set(mapOf("vote" to FieldValue.increment(vote.toLong())), SetOptions.merge())
                .addOnSuccessListener {
                    Log.d("VoteSharding", "Success")
                }.addOnFailureListener {
                    Log.d("VoteSharding", "Failed")
                }
        }





    }

}