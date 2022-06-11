package sebastian.company.min3rapp.ui.miner_home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.data.remote.dto.articles.ArticlesDto
import sebastian.company.min3rapp.data.remote.dto.articles.DataDto
import sebastian.company.min3rapp.data.remote.dto.articles.toArticle
import sebastian.company.min3rapp.data.remote.dto.articles.toDataArticle
import sebastian.company.min3rapp.domain.model.*

class MinerHomeViewModel : ViewModel() {

    private lateinit var functions: FirebaseFunctions
    private var _requestState = MutableLiveData<DataListState>()
    val requestState: LiveData<DataListState>
        get() = _requestState

    private val _articlesState = MutableStateFlow(ArticlesListState())
    val articlesState = _articlesState.asStateFlow()

    private val adsState: AdsState = AdsState()


    private fun articlesCloudFunction(): Task<List<DataArticle>> {
        _requestState.value = DataListState(isLoading = true)
        functions = Firebase.functions
        // put the
        val data = hashMapOf(
            "text" to "My name is sebastian",
        )

        return functions
            .getHttpsCallable("getCryptoNews")
            .call(data)
            .continueWith { task ->
                //If it fails to make make Json ArticleDto Object then we can set ArticleListState
                //error to true or something
                val json =  Gson().toJson(task.result.data)
                val result = Gson().fromJson<DataDto>(json, DataDto::class.java)
                val response = result.articles!!.map { it.toDataArticle() }

                response
            }
    }



    fun getArticlesCf(){

        articlesCloudFunction()
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
                    if(task.result.isNotEmpty()){
                        introduceAds(task.result)
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

        viewModelScope.launch{
            //give ads 1 second to load
            delay(1000)
            if(adsState.ads.isEmpty()){
                _requestState.value = DataListState(articles = data)
            }else{
                val articles = data.toMutableList()
                for(i in 0 until adsState.ads.size){
                    //ad every 6 articles
                    val index = (i + 1) * 6
                    articles.add(index = index, DataArticle(nativeAd = adsState.ads[i]))
                }
                _requestState.value = DataListState(articles = articles)
            }

        }

    }

}