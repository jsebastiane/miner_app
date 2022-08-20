package sebastian.company.min3rapp.ui.discuss

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.common.fakeForumComments
import sebastian.company.min3rapp.common.fakeForumCommentsList
import sebastian.company.min3rapp.domain.model.discuss.ForumComment
import sebastian.company.min3rapp.ui.discuss.composables.CommentCompose
import sebastian.company.min3rapp.ui.theme.TransparentGrey
import sebastian.company.min3rapp.ui.theme.Typography

@Composable
fun CommentDetailsMain(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.brand_darkblue))) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CommentFocused(
                mainComment = ForumComment(
                    "201931", "This is a cool " +
                            "article are there more like this one?", 0, 23
                )
            )
            
            NestedComments(nestedComments = fakeForumCommentsList())

//            NoNestedComments()
        }

        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = colorResource(id = R.color.brand_pink),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_comments),
                contentDescription = "comment",
                tint = Color.White
            )
        }
    }

}

@Composable
fun NoNestedComments(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(modifier = Modifier.align(alignment = Alignment.Center),
            text = "No comments yet",
            style = Typography.h1)
    }
}


@Composable
fun CommentFocused(mainComment: ForumComment){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                border = BorderStroke(1.dp, colorResource(id = R.color.brand_pink)),
                RoundedCornerShape(16.dp)
            )
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
                    text = mainComment.text,
                    style = Typography.caption,
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    overflow = TextOverflow.Ellipsis

                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier
                            .size(12.dp),
                        painter = painterResource(id = R.drawable.ic_comments),
                        contentDescription = "comments"

                    )
                    Text(
                        text = "${mainComment.nestedReplies}",
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
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = mainComment.votes.toString(),
                    style = Typography.caption,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 9.dp)

                )

                Image(
                    painter = painterResource(id = R.drawable.ic_downvote),
                    contentDescription = "upvote",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NestedComments(nestedComments: List<ForumComment>){
    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp),
        ) {

                itemsIndexed(nestedComments) { index, comment ->
                    CommentCompose(comment)
                    if(index == nestedComments.size-1){
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }
    }




@Preview
@Composable
fun CommentViewTest(){
    CommentDetailsMain()
}


