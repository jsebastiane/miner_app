package sebastian.company.min3rapp.ui.discuss.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import sebastian.company.min3rapp.domain.model.discuss.ForumComment
import sebastian.company.min3rapp.domain.model.discuss.ForumCommentsState

class DiscussDetailsViewModel(private val topicId: String): ViewModel() {


    private val forumTopicDB = FirebaseFirestore.getInstance()
    private val forumPath = forumTopicDB
        .collection("forumTopics")

    private val shards: Int = 5
    //The document id of the current focusedTopic
    var forumCommentsState by mutableStateOf<ForumCommentsState>(ForumCommentsState())
        private set

    init {
        getForumComments(topicId)
    }


    private fun getForumComments(documentId: String){
        forumCommentsState = ForumCommentsState(loading = true)
        val tempList = mutableListOf<ForumComment>()
        forumPath.document(documentId).collection("comments")
            .get()
            .addOnSuccessListener { documents ->
                for(doc in documents){
                    val comment = doc.toObject<ForumComment>()
                    tempList.add(comment)
                    Log.d("Comments", comment.toString())
                }
                forumCommentsState = ForumCommentsState(forumComments = tempList)
            }.addOnFailureListener {
                forumCommentsState = ForumCommentsState(error = "Failed to load comments")
            }
    }



    fun userUpvote(){
//        Choose random number between 0 and 20(or whatever number i have placed in the shardNumber
//        field
//        Update firestoreDB.collection("forumMin3r").doc("forum").collection("forumTopics").doc(topicId) Increment(1)
//
        val randomNumber = (1..shards).random()


    }

    fun userDownVote(){

    }

    fun userRemoveVote(){

    }

    fun addComment(){}
}