package sebastian.company.min3rapp.ui.discuss.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.domain.model.discuss.ForumComment
import sebastian.company.min3rapp.ui.theme.TransparentGrey
import sebastian.company.min3rapp.ui.theme.Typography

@Composable
fun CommentCompose(comment: ForumComment){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.brand_darkblue))
            .padding(horizontal = 14.dp)
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
                        text = comment.votes.toString(),
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
    Spacer(
        modifier = Modifier
            .height(8.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.brand_darkblue))
    )
}

@Preview
@Composable
fun TestComment(){
    CommentCompose(comment = ForumComment("201931", "This is a cool " +
            "article are there more like this one?", 0, 23))
}