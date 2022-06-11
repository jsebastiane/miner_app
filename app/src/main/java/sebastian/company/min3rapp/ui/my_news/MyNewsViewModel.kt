package sebastian.company.min3rapp.ui.my_news

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.common.UserPrefs
import sebastian.company.min3rapp.data.remote.dto.articles.DataDto
import sebastian.company.min3rapp.data.remote.dto.articles.toDataArticle
import sebastian.company.min3rapp.domain.model.AdsState
import sebastian.company.min3rapp.domain.model.ArticlesListState
import sebastian.company.min3rapp.domain.model.DataArticle
import sebastian.company.min3rapp.domain.model.DataListState

class MyNewsViewModel(application: Application): AndroidViewModel(application){

    private lateinit var functions: FirebaseFunctions
//    private var mAuth = FirebaseAuth.getInstance()
//    private val mFirebaseDB = FirebaseFirestore.getInstance()
//    private val userRef = mFirebaseDB.collection("user").document("user")
    private val adsState: AdsState = AdsState()
    private val userPreferences = UserPrefs(getApplication())
    private var _requestState = MutableLiveData<DataListState>()
    val requestState: LiveData<DataListState>
        get() = _requestState
    val myTopics: LiveData<String> = userPreferences.getMyTopics().asLiveData()



    //Add parameters to this function to customize article response
    private fun articlesCloudFunction(topics: String): Task<List<DataArticle>> {

        functions = Firebase.functions
        // put the
        val data = hashMapOf(
            "tickers" to topics,
        )

        return functions
            .getHttpsCallable("getMyNews")
            .call(data)
            .continueWith { task ->
                //If it fails to make make Json ArticleDto Object then we can set ArticleListState
                //error to true or something
                val json =  Gson().toJson(task.result.data)
                val result = Gson().fromJson<DataDto>(json, DataDto::class.java)
                val response = result.articles!!.map { it.toDataArticle() }
//                val response = result.articleDtos!!.map { it.toArticle() }
//                val result = task.result?.dataDto.toString()
//                println(result.status)
                response
            }
    }



    fun getArticlesCf(topics: String){
        _requestState.value = DataListState(isLoading = true)
        articlesCloudFunction(topics)
            .addOnCompleteListener(OnCompleteListener { task ->
                if(!task.isSuccessful){
                    val e = task.exception
                    if(e is FirebaseFunctionsException){
                        val code = e.code
                        val details = e.details
                        Log.d("CloudError", details.toString())
                    }
                    _requestState.value = DataListState(error = "Error retrieving articles")
                }else{
                    Log.d("SUCCESS", "Successfully Called CF")
                    //only if ads are not empty and articles is not empty do we introduce ads
                    if(task.result.isNotEmpty()){
                        if(adsState.ads.isNotEmpty()){
                            introduceAds(task.result)
                        }else{
                            _requestState.value = DataListState(articles = task.result)
                        }
                    }else{
                        _requestState.value = DataListState(error = "Oops! No articles found...")
                    }

                }

            })

    }

    fun setAds(ad: NativeAd){
        adsState.ads.add(ad)
    }

    private fun introduceAds(data: List<DataArticle>){
        val articles = data.toMutableList()
        for(i in 0 until adsState.ads.size){
            //placing ad every 4 articles
            val index = (i + 1) * 5
            articles.add(index = index, DataArticle(nativeAd = adsState.ads[i]))
        }
        _requestState.value = DataListState(articles = articles)

    }


}