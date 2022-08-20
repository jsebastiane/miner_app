package sebastian.company.min3rapp.ui.discover

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.common.UserPrefs
import sebastian.company.min3rapp.common.fakeDiscoverList
import sebastian.company.min3rapp.common.fakeForumArticles
import sebastian.company.min3rapp.domain.model.DiscoverItem
import sebastian.company.min3rapp.domain.model.DiscoverItemState


class DiscoverViewModel(application: Application): AndroidViewModel(application) {

    private val discoverDB = FirebaseFirestore.getInstance()
    private val discoverReference = "discoverResources"

    

    private var discoverItems: List<DiscoverItem> = listOf()
    var discoverState by mutableStateOf<DiscoverItemState>(DiscoverItemState())
        private set

    //default should be set somewhere
    var query: String = "learning"

    init {
        getDiscoverData()
//        addTempForumArticles()
    }

//    fun addTempForumArticles(){
//        val path = discoverDB.collection("forumTopics")
//        for(i in fakeForumArticles()){
//            path.add(i)
//        }
//    }


    private fun getDiscoverData(){
        discoverState = DiscoverItemState(isLoading = true)
        discoverDB.collection(discoverReference).addSnapshotListener { snapshot, e ->
            if(e != null){
                Log.w("firestore", "listen failed", e)
            }

            if(snapshot != null){
                val snapshotList = mutableListOf<DiscoverItem>()
                val documents = snapshot.documents
                documents.forEach {
                    val discoverItem = it.toObject(DiscoverItem::class.java)
                    if(discoverItem != null){
                        snapshotList.add(discoverItem)
                    }
                }
                discoverItems = snapshotList
                Log.d("Items", discoverItems.toString())
                categoryQuery()

            }
        }
    }


    fun categoryQuery(){
        val queriedList = discoverItems.filter {
            it.tags.contains(query.lowercase())
        }
        discoverState = DiscoverItemState(discoverItems = queriedList)
    }


}