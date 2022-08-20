package sebastian.company.min3rapp.ui.discuss

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sebastian.company.min3rapp.ui.discuss.viewmodel.DiscussDetailsViewModel

class DiscussDetailsViewModelFactory(private val topicId: String): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscussDetailsViewModel(topicId) as T
    }
}