package sebastian.company.min3rapp.ui.discuss.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sebastian.company.min3rapp.common.ForumDataType

class ForumViewModelFactory(private val dataType: ForumDataType,
private val articleId: String?,
private val commentId: String?): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ForumViewModel(dataType, articleId, commentId) as T
    }
}