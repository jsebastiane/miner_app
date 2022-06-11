package sebastian.company.min3rapp.ui.discover

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.common.UserPrefs

class DiscoverViewModel(application: Application): AndroidViewModel(application) {

    private val userPreferences = UserPrefs(getApplication())
    val memoryHighScore: LiveData<Int> = userPreferences
        .getMemoryHighScore().asLiveData()

}