package sebastian.company.min3rapp.domain.model.discuss

data class VoteState(
    val success: Boolean? = null,
    var loading: Boolean? = null,
    val error: String? = null
)
