package sebastian.company.min3rapp.ui.miner_home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.data.remote.dto.articles.DataDto
import sebastian.company.min3rapp.data.remote.dto.articles.toDataArticle
import sebastian.company.min3rapp.domain.model.*
import java.util.*

class MinerHomeViewModel : ViewModel() {

    private lateinit var functions: FirebaseFunctions
    private var _articlesState = MutableLiveData<ArticlesListState>()
    val articlesState: LiveData<ArticlesListState>
        get() = _articlesState

    private val firestoreDB = FirebaseFirestore.getInstance()
    private val articlesDB = firestoreDB.collection("articles")



    private val adsState: AdsState = AdsState()


    fun getArticles(){
        _articlesState.value = ArticlesListState(isLoading = true)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)

        val time = Timestamp(calendar.time)

        val snapShotList = mutableListOf<Article>()

        articlesDB.whereGreaterThan("dbAddDate", time)
            .get()
            .addOnSuccessListener { documents ->
                for(doc in documents){
                    val article = doc.toObject(Article::class.java)
                    snapShotList.add(article)
                }
                Log.d("ExistingList", snapShotList.toString())
                _articlesState.value = ArticlesListState(articles = snapShotList)

            }.addOnFailureListener {
                _articlesState.value = ArticlesListState(error = "Error retrieving forum topics", isLoading = false)
            }
    }

    fun setAds(ad: NativeAd){
        adsState.ads.add(ad)
    }

    private fun introduceAds(data: List<Article>){
        viewModelScope.launch{
            //give ads 1 second to load
            delay(1000)
            if(adsState.ads.isEmpty()){
                _articlesState.value = ArticlesListState(articles = data)
            }else{
                val articles = data.toMutableList()
                for(i in 0 until adsState.ads.size){
                    //ad every 6 articles
                    val index = (i + 1) * 6
                    articles.add(index = index, Article(nativeAd = adsState.ads[i]))
                }
                _articlesState.value = ArticlesListState(articles = articles)
            }

        }

    }

}