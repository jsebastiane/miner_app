package sebastian.company.min3rapp.common

import sebastian.company.min3rapp.domain.model.Article
import sebastian.company.min3rapp.domain.model.DiscoverItem
import sebastian.company.min3rapp.domain.model.discuss.ForumArticle
import sebastian.company.min3rapp.domain.model.discuss.ForumComment
import java.text.DecimalFormat


fun formatPrice(price: Double) : String{

    var decFormat = DecimalFormat("")
    val dec1 = DecimalFormat("$###,###.##")
    val dec2 = DecimalFormat("$#.#####")
    val dec3 = DecimalFormat("$#.########")


    if(price >= 1){
        decFormat = dec1
    }else if(price < 1 && price >= 0.0001){
        decFormat = dec2
    }else if(price < 0.0001){
        decFormat = dec3
    }

    return decFormat.format(price)

}

fun formatMktCap(marketCap: Long) : String{

    var newMarketCap = ""

    val trilFormat = DecimalFormat("$###.##T")
    val bilFormat = DecimalFormat("$###.##B")

    if(marketCap >= 1000000000000) {
        val tempFigure = marketCap / 1000000000000
        newMarketCap = trilFormat.format(tempFigure)

    } else if (marketCap <= 999999999999){
        val tempFigure = marketCap / 1000000000
        newMarketCap = bilFormat.format(tempFigure)
    }

    return newMarketCap
}

fun Double.formatPercentage() = "%.2f%%".format(this)

fun fakeDiscoverList(): List<DiscoverItem>{
    return listOf(
        DiscoverItem("Buildspace", "Buildspace accelerates your builder journey " +
                "into web3. Whether you're just starting out, a seasoned vet transitioning from web2," +
                " or thinking of building something cool, you have a home with us. Join 60k+ incredible" +
                " builders doing just that", "https://pbs.twimg.com/profile_images/1422256068554641413/iR0aoxSO_400x400.jpg",
            listOf("learning resources", "hackathons"),
            "https://buildspace.so/projects"),
        DiscoverItem("Secureum", "Buildspace accelerates your builder journey " +
                "into web3. Whether you're just starting out, a seasoned vet transitioning from web2," +
                " or thinking of building something cool, you have a home with us. Join 60k+ incredible" +
                " builders doing just that", "https://pbs.twimg.com/profile_images/1422256068554641413/iR0aoxSO_400x400.jpg",
            listOf("learning resources", "work"),
            "https://buildspace.so/projects"),
        DiscoverItem("Ethereum.org", "Buildspace accelerates your builder journey " +
                "into web3. Whether you're just starting out, a seasoned vet transitioning from web2," +
                " or thinking of building something cool, you have a home with us. Join 60k+ incredible" +
                " builders doing just that", "https://pbs.twimg.com/profile_images/1422256068554641413/iR0aoxSO_400x400.jpg",
            listOf("hackathons", "work"),
            "https://buildspace.so/projects"),
        DiscoverItem("Min3r App", "Buildspace accelerates your builder journey " +
                "into web3. Whether you're just starting out, a seasoned vet transitioning from web2," +
                " or thinking of building something cool, you have a home with us. Join 60k+ incredible" +
                " builders doing just that", "https://pbs.twimg.com/profile_images/1422256068554641413/iR0aoxSO_400x400.jpg",
            listOf("work"),
            "https://buildspace.so/projects")
    )
}

fun fakeForumComments(): Map<Int, List<ForumComment>>{
    val tempMap = mutableMapOf<Int, List<ForumComment>>()
    val tempList = mutableListOf<ForumComment>()
    for(i in 0..150){
        if(i % 2 == 0) {
            tempList.add(
                ForumComment("","98192031",
                    "Problem is he has a large influence on people who are just now able to " +
                            "vote or will be able to soon",
                    142,
                    25
                )
            )
        }else{
            tempList.add(
                ForumComment("","0918821412",
                    "Well, technically 'crypto' isn't the technology. The technology is " +
                            "blockchain or DAG and it uses cryptography for security. Universities are teaching the technology - blockchain, not 'crypto'.\n" +
                            "The term 'crypto' in popular culture refers a combination of technologies consisting of complex systems",
                    142,
                    156
                )
            )
        }
    }
    tempMap[0] = listOf<ForumComment>()
    tempMap[1] = tempList
    return tempMap
}

fun fakeForumCommentsList(): List<ForumComment>{
    val tempList = mutableListOf<ForumComment>()
    for(i in 0..10){
        if(i % 2 == 0) {
            tempList.add(
                ForumComment("","98192031",
                    "Problem is he has a large influence on people who are just now able to " +
                            "vote or will be able to soon",
                    142,
                    25
                )
            )
        }else{
            tempList.add(
                ForumComment("","0918821412",
                    "Well, technically 'crypto' isn't the technology. The technology is " +
                            "blockchain or DAG and it uses cryptography for security. Universities are teaching the technology - blockchain, not 'crypto'.\n" +
                            "The term 'crypto' in popular culture refers a combination of technologies consisting of complex systems",
                    142,
                    156
                )
            )
        }
    }
    return tempList
}

fun fakeForumArticles(): List<ForumArticle>{
    return listOf(
        ForumArticle(Article(source = "Bitcoin Magazine",
            title = "Federated Sidechains Are Bitcoin's Original Upgradeable Sidechain Implementation," +
                    "in addition to establishing poo poo and spaghetti",
            urlToImage =  "https://crypto.snapi.dev/images/v1/x/z/img-7112-172273.png"),
            articleId = "1",
            views = 245674,
            commentsCount = 73
            ),
        ForumArticle(Article(source = "FXEmpire",
            title = "Bitcoin (BTC) Fear & Greed Index Slides to the Edge of Extreme Fear",
            urlToImage =  "https://crypto.snapi.dev/images/v1/b/o/shutterstock-1930531490-7-172274.jpg"),
            articleId = "2",
            views = 134,
            commentsCount = 55
        ),
        ForumArticle(Article(source = "FXEmpire",
            title = "Crypto Market Daily Highlights – BTC and the Broader Market Hit Reverse",
            urlToImage =  "https://crypto.snapi.dev/images/v1/3/7/shutterstock-2153066245-6-172266.jpg"),
            articleId = "3",
            views = 121,
            commentsCount = 46
        ),
        ForumArticle(Article(source = "Benzinga",
            title = "So Do You Believe Bitcoin Is Going Above \$40K, Ethereum Above \$3K And Dogecoin Above 20 Cents By End Of 2022?",
            urlToImage =  "https://crypto.snapi.dev/images/v1/h/v/doge-ethereum-bitcoin-172263.jpg"),
            articleId = "4",
            views = 91,
            commentsCount = 61
        ),
        ForumArticle(Article(source = "Bitcoin",
            title = "Tesla Reveals Bitcoin Holdings Worth \$222 Million in Latest SEC Filing",
            urlToImage =  "https://crypto.snapi.dev/images/v1/n/y/tesla-sec-172261.jpg"),
            articleId = "5",
            views = 81,
            commentsCount = 11
        ),
        ForumArticle(Article(source = "Bitcoinist",
            title = "Bitcoin Addresses In Profit Falls Below 50%",
            urlToImage =  "https://crypto.snapi.dev/images/v1/g/3/bitcoin-2-630x420-172253.jpeg"),
            articleId = "6",
            views = 39,
            commentsCount = 5
        ),
        ForumArticle(Article(source = "Inside Bitcoins",
            title = "Bitcoin Price Prediction for Today, July 25: BTC Trades Below \$22,000 as Bears Show-up",
            urlToImage =  "https://crypto.snapi.dev/images/v1/c/l/crypto17-172229.jpg"),
            articleId = "7",
            views = 15,
            commentsCount = 2
        )
    )
}

//tempList.forEachIndexed { index, forumComment ->
//            if(user!!.currentReactionsMap.keys.any { it == forumComment.commentId }){
//                tempList.remove(forumComment)
//                tempList.add(index, ForumComment(poster = forumComment.poster, commentId = forumComment.commentId,
//                    text = forumComment.text, nestedReplies = forumComment.nestedReplies, votes = forumComment.votes,
//                    timePosted = forumComment.timePosted, userReaction = user!!.currentReactionsMap[forumComment.commentId]!!,
//                timeSincePosted = forumComment.timeSincePosted))
//            }
//        }



