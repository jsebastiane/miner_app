package sebastian.company.min3rapp.ui.discuss

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import sebastian.company.min3rapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import sebastian.company.min3rapp.common.ForumDataType
import sebastian.company.min3rapp.domain.model.discuss.ForumComment
import sebastian.company.min3rapp.ui.discuss.composables.WriteComment
import sebastian.company.min3rapp.ui.discuss.viewmodel.DiscussDetailsViewModel
import sebastian.company.min3rapp.ui.discuss.viewmodel.ForumViewModel
import sebastian.company.min3rapp.ui.discuss.viewmodel.ForumViewModelFactory
import sebastian.company.min3rapp.ui.theme.TransparentGrey
import sebastian.company.min3rapp.ui.theme.Typography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DiscussDetailsMain(
    articleId: String,
    articleTitle: String,
    imageUrl: String,
    navigateToComment: (ForumComment, String) -> Unit,
    viewArticle: () -> Unit,
    viewModel: ForumViewModel = viewModel(
        factory =
        ForumViewModelFactory(ForumDataType.FORUM_COMMENT, articleId, null),

    )
) {

    val commentsState = viewModel.forumCommentsState
    val addCommentState = viewModel.addCommentState
    val focusRequest = FocusRequester()
//    val addCommentState = viewModelSec.addCommentState


    BoxWithConstraints(
        modifier = Modifier
            .background(colorResource(id = R.color.brand_darkblue))
            .fillMaxSize()
    ) {

        AsyncImage(
            model = imageUrl,
            contentDescription = "NewsImage",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            SeeArticleView(seeArticle = {
                viewArticle()
            })

//                    AnimatedVisibility(visible = visible) {
//                        ErrorComments(modifier = Modifier.fillMaxWidth().weight(1F))
//                    }


//                        visible = false
            if (commentsState.loading) {
                DiscussDetailsLoading()
            }

            if (commentsState.forumComments.isNotEmpty()) {
                CommentSection(Modifier.weight(1F),
                    mapOf(0 to listOf(), 1 to commentsState.forumComments),
                    articleTitle,
                    commentClicked = { item ->
                        navigateToComment(item, articleId)
                    },
                    propVote = {key, userStateVote, commentStateVotes ->
                        viewModel.commentVote(key, userStateVote, commentStateVotes)
                    }
                )
            } else {
                NoComments(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F), articleTitle)
            }

//            Spacer(
//                modifier = Modifier
//                    .background(colorResource(id = R.color.brand_pink))
//                    .height(2.dp)
//            )
            if (addCommentState.loading){
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = colorResource(id = R.color.brand_pink),
                )
            }else{
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    progress = 1F,
                    color = colorResource(id = R.color.brand_pink),
                )
            }

            WriteComment(focusRequest = focusRequest, sendComment = { comment ->
                viewModel.addComment(comment)
            }, addCommentState.loading)


        }


    }

}

@Composable
fun DiscussDetailsLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center),
            color = colorResource(id = R.color.brand_pink)
        )
    }
}

@Composable
fun NoComments(modifier: Modifier = Modifier,
    title: String) {

        Column(modifier = modifier.then(Modifier.fillMaxSize())) {
            EmptySpaceView()

            Text(
                text = title,
                style = Typography.h4,
                modifier = Modifier.padding(16.dp)
            )
            Box(modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center){
                Text(modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "No Comments",
                    style = Typography.h1
                )
            }

            }



}


@Composable
fun ErrorComments(modifier: Modifier = Modifier) {


    Box(modifier = modifier.then(Modifier.fillMaxSize())) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Error",
            style = Typography.h1
        )
    }

}

@Composable
fun ArticleComment(
    comment: ForumComment,
    onCommentClicked: (ForumComment) -> Unit,
    //commentId, userVoteState, commentVoteState
    onVote: (String, Int, Int) -> Unit

) {

    var reaction by remember { mutableStateOf(comment.userReaction)}
    //This will work when I implement sharding likes
    var currentVotes by remember{ mutableStateOf(comment.votes)}

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.brand_darkblue))
            .padding(horizontal = 14.dp)
            .clickable { onCommentClicked(comment) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.brand_darkblue_variant))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(5f),

                    ) {
                    Text(
                        text = "anonymous001092813",
                        style = Typography.subtitle1,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                    )

                    Text(
                        text = comment.text,
                        style = Typography.caption,
                        modifier = Modifier
                            .padding(bottom = 12.dp),
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis

                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Reply",
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(0.5.dp, TransparentGrey, RoundedCornerShape(8.dp))
                                .padding(vertical = 4.dp, horizontal = 16.dp),
                            style = Typography.body2
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            modifier = Modifier
                                .size(12.dp),
                            painter = painterResource(id = R.drawable.ic_comments),
                            contentDescription = "comments"

                        )
                        Text(
                            text = "${comment.nestedReplies}",
                            modifier = Modifier
                                .padding(2.dp),
                            style = Typography.body2
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Top
                ) {

                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_upvote),
                        contentDescription = "upvote",
                        colorFilter = ColorFilter.tint(
                            if(reaction == 1) colorResource(id = R.color.brand_pink)
                            else Color.LightGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (reaction) {
                                    -1 -> {
                                        reaction = 1
                                        //The difference between -1 and 1 is 2 so we update current
                                        // votes accordingly
                                        currentVotes += 2
                                        onVote(comment.commentId, 1, 2)
                                    }
                                    0 -> {
                                        reaction = 1
                                        currentVotes += 1
                                        onVote(comment.commentId, 1, 1)
                                    }
                                    1 -> {
                                        //Removing vote so reaction is 0 (1-1)
                                        reaction = 0
                                        currentVotes -= 1
                                        onVote(comment.commentId, 0, -1)
                                    }
                                }
                            }
                    )

                    Text(
                        text = currentVotes.toString(),
                        style = Typography.caption,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 9.dp)

                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_downvote),
                        contentDescription = "downvote",
                        colorFilter = ColorFilter.tint(
                            if(reaction == -1) colorResource(id = R.color.brand_pink)
                            else Color.LightGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when (reaction) {
                                    -1 -> {
                                        reaction = 0
                                        currentVotes += 1
                                        onVote(comment.commentId, 0, 1)
                                    }
                                    0 -> {
                                        reaction = -1
                                        currentVotes -= 1
                                        onVote(comment.commentId, -1, -1)
                                    }
                                    1 -> {
                                        reaction = -1
                                        currentVotes -= 2
                                        onVote(comment.commentId, -1, -2)
                                    }
                                }
                            }
                    )
                }
            }

        }

    }
    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.brand_darkblue))
    )


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommentSection(
    modifier: Modifier = Modifier, data: Map<Int, List<ForumComment>>, title: String,
    commentClicked: (ForumComment) -> Unit,
    propVote: (String, Int, Int) -> Unit
) {
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier.then(Modifier.fillMaxWidth()),
//            modifier = Modifier
//                .fillMaxWidth()
        ) {
            data.forEach { (number, comments) ->
                stickyHeader {
                    if (number == 0) {
                        EmptySpaceView()
                    } else {
                        ArticleTitleView(title)
                    }
                }
                items(comments) { comment ->
                    ArticleComment(comment, onCommentClicked = { item ->
                        commentClicked(item)
                    }, onVote = {key, userStateVote, commentStateVotes ->
                        propVote(key, userStateVote, commentStateVotes)
                    })
                }
            }
        }
    }
}

@Composable
fun ArticleTitleView(title: String) {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val tabRowSize = width / 2.5
    val items = (0..1)
    var activeTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.brand_darkblue)),
        verticalArrangement = Arrangement.Top

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.brand_darkblue))
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = title,
                style = Typography.h4,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        TabRow(
            selectedTabIndex = activeTabIndex,
            modifier = Modifier
                .width(tabRowSize.dp)
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(10.dp)),
            backgroundColor = colorResource(id = R.color.brand_darkblue_variant),
            indicator = {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(it[activeTabIndex])
                        .background(colorResource(id = R.color.brand_pink))
                        .fillMaxSize()
                        .zIndex(-1F)
                )
            },
        ) {

            items.mapIndexed { index, item ->
                Tab(selected = activeTabIndex == index, onClick = { activeTabIndex = index }) {
                    Icon(
                        painter = if (index == 0) painterResource(id = R.drawable.ic_fire) else painterResource(
                            id = R.drawable.ic_timer
                        ),
                        contentDescription = "fire",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .size(18.dp)
                    )
                }
            }

        }


    }

}

@Composable
fun EmptySpaceView() {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val spacerSize = width / 2 - 50

    Spacer(
        modifier = Modifier
            .height(spacerSize.dp)
            .fillMaxWidth()
    )

}

@Composable
fun SeeArticleView(seeArticle: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(end = 16.dp)


    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(0.5.dp, TransparentGrey, RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.brand_darkblue_variant))
                .align(alignment = Alignment.CenterEnd)
                .clickable {
                    seeArticle()
                }
        ) {
            Text(
                text = "See Article",
                style = Typography.h5,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .padding(8.dp)
            )
        }
    }
}

//@Composable
//fun Testing(){
//
//}

//@Preview
//@Composable
//fun DetailsTest(){
//    DiscussDetailsMain("So Do You Believe Bitcoin Is Going Above \$40K, Ethereum Above \$3K And Dogecoin Above 20 Cents By End Of 2022?",
//    "9182903u710", "")
//}
