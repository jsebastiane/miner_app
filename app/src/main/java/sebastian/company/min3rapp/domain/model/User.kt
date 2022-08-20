package sebastian.company.min3rapp.domain.model

import com.google.firebase.Timestamp

data class User(
    val userId: String,
    val dateJoined: Timestamp,
    val currentReactionsDoc: String,
    val currentCommentsDoc: String
)

