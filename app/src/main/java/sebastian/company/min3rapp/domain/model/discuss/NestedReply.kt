package sebastian.company.min3rapp.domain.model.discuss

data class NestedReply(
    val commentId: String,
    val text: String,
    //number of replies to this comment
    val votes: Int
)
