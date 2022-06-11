package sebastian.company.min3rapp.ui.discover.distractions.memory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.common.UserPrefs

class MemoryGameViewModel(application: Application): AndroidViewModel(application) {

    private val userPreferences = UserPrefs(getApplication())
    private val _memoryGameHighScore: LiveData<Int> = userPreferences
        .getMemoryHighScore().asLiveData()
    val memoryGameHighScore: LiveData<Int>
        get() = _memoryGameHighScore
    private var highScoreVM: Int? = null





    fun prepareCells(cellsToMake: Int): List<Int> {
        var count = 1
        val cellList = ArrayList<Int>()
        for (i in 1..24) {
            if (count <= cellsToMake) {
                cellList.add(i)
                count += 1
            } else {
                cellList.add(0)
            }
        }

        return cellList.shuffled()
    }


    fun updateHighScore(newScore: Int){
        if(highScoreVM != null){
            if(newScore > highScoreVM!!){
                viewModelScope.launch {
                    userPreferences.saveMemoryHighScore(newScore)
                }
            }
        }else{
            viewModelScope.launch {
                userPreferences.saveMemoryHighScore(newScore)
            }
        }

    }

    fun setLocalHighScore(highScore: Int){
        highScoreVM = highScore
    }
}