package sebastian.company.min3rapp.ui.my_news.news_tags

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.common.collect.ArrayTable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.common.Constants
import sebastian.company.min3rapp.common.UserPrefs
import sebastian.company.min3rapp.domain.model.NewsTag
import java.util.*
import kotlin.collections.ArrayList

class NewsTagsViewModel(application: Application): AndroidViewModel(application) {

    private val userPreferences = UserPrefs(getApplication())
    private var myNewsTags: String? = null
    //only observed when closing fragment
    private val _saveComplete = MutableLiveData<Boolean>()
    val saveComplete: LiveData<Boolean>
        get() = _saveComplete
    private val _myTags = MutableLiveData<List<String>>()
    val myTags: LiveData<List<String>>
        get() = _myTags

    init {
        viewModelScope.launch {
            myNewsTags = userPreferences.getMyTopics().first()
            formatTags()
        }
    }


    //turn tags into NewsTag object and introduce user topics previously chosen (if any)
    private fun formatTags(){
        if(!myNewsTags.isNullOrEmpty()){
            _myTags.value = myNewsTags!!.split(",").toList()
        }else{
            //empty list if user preferences returns or empty
            _myTags.value = arrayListOf()
        }
    }

    fun saveNewTags(newsTags: List<String>){
        //save all tags that are checked and merge into string for datastore
        val mergedTags = newsTags.joinToString(",")

        viewModelScope.launch {
            userPreferences.saveMyTopics(mergedTags)
            _saveComplete.value = true
        }

    }


}