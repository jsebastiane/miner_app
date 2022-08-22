package sebastian.company.min3rapp.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class User(
    val userId: String = "",
    @ServerTimestamp
    val dateJoined: Timestamp? = null,
    val currentReactionsMap: Map<String, Int> = mapOf(),
    val currentCommentsMap: Map<String, Int> = mapOf()
)

