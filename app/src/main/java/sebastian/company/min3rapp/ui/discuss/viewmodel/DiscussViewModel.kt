package sebastian.company.min3rapp.ui.discuss.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import sebastian.company.min3rapp.domain.model.discuss.ForumArticle
import sebastian.company.min3rapp.domain.model.discuss.ForumArticleState

class DiscussViewModel: ViewModel() {

    private val forumArticlesDB = FirebaseFirestore.getInstance()
    private val forumPath = forumArticlesDB
        .collection("forumTopics")

    private var forumArticles: List<ForumArticle> = listOf()
    var forumArticlesState by mutableStateOf<ForumArticleState>(ForumArticleState())
        private set

    init {
        getDailyArticles()
    }

    //will add filter later
    fun getDailyArticles(){
        forumArticlesState = ForumArticleState(loading = true)
        val tempList = mutableListOf<ForumArticle>()
        forumPath.get().addOnSuccessListener { documents ->
            for(doc in documents){
                val article = doc.toObject<ForumArticle>()
                tempList.add(article)
            }

            forumArticlesState = ForumArticleState(forumArticles = tempList, loading = false)


        }.addOnFailureListener {
            forumArticlesState = ForumArticleState(error = "Error retrieving forum topics", loading = false)
        }
    }

}