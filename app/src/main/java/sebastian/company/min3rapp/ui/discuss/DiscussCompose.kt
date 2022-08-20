package sebastian.company.min3rapp.ui.discuss

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import sebastian.company.min3rapp.R
import androidx.lifecycle.viewmodel.compose.viewModel
import sebastian.company.min3rapp.domain.model.discuss.ForumArticle
import sebastian.company.min3rapp.ui.discuss.viewmodel.DiscussViewModel
import sebastian.company.min3rapp.ui.theme.BrandPink2
import sebastian.company.min3rapp.ui.theme.TransparentBlack
import sebastian.company.min3rapp.ui.theme.TransparentGrey
import sebastian.company.min3rapp.ui.theme.Typography

@Composable
fun DiscussPage(viewModel: DiscussViewModel = viewModel(),
                navigateToArticleForum: (ForumArticle) -> Unit,
                ){

    val forumState = viewModel.forumArticlesState

    Box(modifier = Modifier
        .background(colorResource(id = R.color.brand_darkblue))
        .fillMaxSize()){
        Column(modifier = Modifier.padding(horizontal = 16.dp)){
            ChipSection(chips = listOf("Popular", "New"), onCategoryChange = {/*Todo*/})
            if(forumState.forumArticles.isNullOrEmpty()){
                Text(text = "No articles")
            }else{
                ForumSection(articles = forumState.forumArticles, articleAction = {article ->
                    navigateToArticleForum(article)
                })
            }

        }
    }
}


@Composable
fun ForumArticle(forumArticle: ForumArticle,
                 onArticleClick: (ForumArticle) -> Unit){

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(170.dp)
        .padding(bottom = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(colorResource(id = R.color.brand_darkblue_variant))
        .clickable { onArticleClick(forumArticle) }) {

        Row(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp,
        start = 16.dp, end = 16.dp)){
            Text(modifier = Modifier
                .weight(3f)
                .padding(bottom = 20.dp),
                text = forumArticle.article.title!!,
                style = Typography.h4,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween) {
                AsyncImage(model = forumArticle.article.urlToImage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    contentDescription = "articleImage",
                    contentScale = ContentScale.Fit,

                    )
                
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp),
                        painter = painterResource(id = R.drawable.ic_views),
                        contentDescription = "views",
                    )
                    Text(modifier = Modifier
                        .padding(end = 8.dp)
                        .align(Alignment.CenterVertically),
                        text = forumArticle.views.toString(),
                        style = Typography.body1)
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp),
                        painter = painterResource(id = R.drawable.ic_comments),
                        contentDescription = "comments"
                    )
                    Text(modifier = Modifier.align(Alignment.CenterVertically),
                        text = forumArticle.commentsCount.toString(),
                        style = Typography.body1)
                }


            }
        }

    }
}

@Composable
fun ForumSection(articles: List<ForumArticle>,
                 articleAction: (ForumArticle) -> Unit){
    LazyColumn(modifier = Modifier.fillMaxWidth()){
        items(articles.size){
            ForumArticle(forumArticle = articles[it], onArticleClick = {article ->
                articleAction(article)
            })
        }
    }
}


@Composable
fun ChipSection(
    chips : List<String>,
    onCategoryChange: (String) -> Unit
){

    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    //this is happening twice as onCategoryChange is called in LazyRow and here
    onCategoryChange(chips[selectedChipIndex])

    //intercept touch event and don't let viewpager be accessed from this tab
    Row(horizontalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxWidth()) {
        for (index in chips.indices) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .weight(1f)
                    .background(
                        if (selectedChipIndex == index) BrandPink2
                        else TransparentBlack
                    )
                    .border(
                        0.5.dp,
                        if (selectedChipIndex == index)
                            colorResource(id = R.color.brand_darkblue)
                        else TransparentGrey,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 6.dp)
            ) {

                Text(
                    text = chips[index],
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 15.dp),
                    style = Typography.h3,
                    color = colorResource(id = R.color.brand_white),
                    fontWeight = FontWeight.ExtraBold

                )

            }
            if(index == 0){
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun ForumChip(chipLabel: String, selected: Boolean){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (selected) BrandPink2
                else TransparentBlack
            )
            .border(
                0.5.dp,
                if (selected) colorResource(id = R.color.brand_darkblue)
                else TransparentGrey,
                RoundedCornerShape(18.dp)
            )
            .padding(vertical = 6.dp)){

        Text(text = chipLabel,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp),
            style = Typography.h3,
            color = colorResource(id = R.color.brand_white),
            fontWeight = FontWeight.ExtraBold

        )

    }
}



@Preview
@Composable
fun Test(){
    DiscussPage(navigateToArticleForum = {it ->
        print(it.article.title)
    })
}