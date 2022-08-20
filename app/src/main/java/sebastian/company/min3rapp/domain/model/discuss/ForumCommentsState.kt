package sebastian.company.min3rapp.domain.model.discuss

data class ForumCommentsState(
    val forumComments: List<ForumComment> = listOf(),
    var loading: Boolean = false,
    val error: String = ""
)
