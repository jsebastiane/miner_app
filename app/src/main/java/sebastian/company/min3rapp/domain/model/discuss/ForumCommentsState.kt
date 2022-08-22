package sebastian.company.min3rapp.domain.model.discuss

data class ForumCommentsState(
    var forumComments: List<ForumComment> = listOf(),
    var loading: Boolean = false,
    var error: String = ""
)
