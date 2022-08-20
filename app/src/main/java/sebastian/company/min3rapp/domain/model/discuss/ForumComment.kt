package sebastian.company.min3rapp.domain.model.discuss

data class ForumComment(
    //val poster: String = ""
    val commentId: String = "",
    val text: String = "",
    //number of replies to this comment
    val nestedReplies: Int = 0,
    val votes: Int = 0,


)
