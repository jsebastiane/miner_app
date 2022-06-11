package sebastian.company.min3rapp.common

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userPreferences")

class UserPrefs(private val context: Context) {

    companion object{
        val MEMORY_GAME_SCORE = intPreferencesKey(name = "memoryHighScore")
        val MY_NEWS_TOPICS = stringPreferencesKey(name = "myNewsTopics")
    }

    suspend fun saveMemoryHighScore(score: Int){
        context.dataStore.edit { preferences ->
            preferences[MEMORY_GAME_SCORE] = score
        }
    }


    fun getMemoryHighScore(): Flow<Int>{
        return context.dataStore.data
            .map { preferences ->
                preferences[MEMORY_GAME_SCORE] ?: 0
            }
    }

    suspend fun saveMyTopics(topics: String){
        context.dataStore.edit{preferences ->
            preferences[MY_NEWS_TOPICS] = topics
        }
    }

    fun getMyTopics(): Flow<String>{
        return context.dataStore.data
            .map { preferences ->
                preferences[MY_NEWS_TOPICS] ?: ""
            }
    }

}