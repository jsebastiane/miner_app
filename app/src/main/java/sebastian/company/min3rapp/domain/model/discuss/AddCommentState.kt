package sebastian.company.min3rapp.domain.model.discuss

data class AddCommentState(
    val loading: Boolean = false,
    val success: Boolean = false,
    val failed: Boolean = false
)
