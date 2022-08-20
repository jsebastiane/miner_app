package sebastian.company.min3rapp.ui.discover


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.domain.model.DiscoverItem
import sebastian.company.min3rapp.ui.theme.*


@Composable
fun DiscoverMenu(viewModel: DiscoverViewModel = viewModel(),
    onNavReqReceived: (DiscoverItem) -> Unit){
    val state = viewModel.discoverState
    Box(modifier = Modifier
        .background(colorResource(id = R.color.brand_darkblue))
        .fillMaxSize()){
        Column(modifier = Modifier.padding(horizontal = 8.dp)){
            ChipSection(chips = listOf("Learn", "Hack",
                "Work", "Invest"), onCategoryChange = {
                    viewModel.query = it
                    viewModel.categoryQuery()
            })
            DiscoverItemSection(discoverItems = state.discoverItems,
                onDiscoverItemClick = {item ->
                    onNavReqReceived(item)
                })

//
//            if (state.isLoading){
//                ProgressDrawable()
//            }else if(state.discoverItems.isNotEmpty()){
//                DiscoverItemSection(discoverItems = state.discoverItems)
//            }
        }

    }


}

@Composable
fun ProgressDrawable(){
    CircularProgressIndicator()
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
    LazyRow {
        items(chips.size){
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp)
                    .clickable {
                        selectedChipIndex = it
//                        onCategoryChange(chips[it])
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (selectedChipIndex == it) BrandPink2
                        else TransparentBlack
                    )
                    .border(
                        0.5.dp,
                        if (selectedChipIndex == it) colorResource(id = R.color.brand_darkblue)
                        else TransparentGrey,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(vertical = 6.dp)

            ) {
               
                Text(text = chips[it],
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 32.dp),
                    style = Typography.h3,
                    color = colorResource(id = R.color.brand_white),
                    fontWeight = FontWeight.ExtraBold,

                )

            }
        }
    }
}

@Composable
fun DiscoverView(discoverItem: DiscoverItem,
    onDiscoverActionPressed: (DiscoverItem) -> Unit){

//    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.brand_darkblue_variant))
    ) {

        Box(modifier = Modifier
            .padding(22.dp)){

            Column{

                Text(text = discoverItem.brandName,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 15.dp),
                    style = Typography.h2)

                Divider(color = BrandPink2, modifier = Modifier.padding(bottom = 8.dp))

                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ){
                        AsyncImage(
                            model = discoverItem.imageUrl,
                            contentDescription = "Resource Image",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(6.dp)))


                    
                    Spacer(modifier = Modifier.width(14.dp))

                    Text(text = discoverItem.tags.toString(),
                    style = Typography.h5, modifier = Modifier
                            .align(alignment = CenterVertically))
                }

                Text(text = discoverItem.description,
                style = Typography.h5,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 16.dp))
                
                Surface(color = BrandPink2,
                    shape = RoundedCornerShape(12.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .clickable {
                            onDiscoverActionPressed(discoverItem)
                        }
                ){
                    
                    Text(text = "Visit Site", style = Typography.h3,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(90.dp),
                    textAlign = TextAlign.Center)

                }

                

            }

        }

    }
}

@Composable
fun DiscoverItemSection(discoverItems: List<DiscoverItem>,
                        onDiscoverItemClick: (DiscoverItem) -> Unit){
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp, start = 8.dp, end = 8.dp),
        modifier = Modifier.fillMaxWidth()){
        items(discoverItems.size){
            DiscoverView(discoverItem = discoverItems[it], onDiscoverActionPressed = {item ->
                onDiscoverItemClick(item)
            })
        }
    }
}




@Preview
@Composable
fun Test(){
    DiscoverMenu(onNavReqReceived = {item ->
        println(item.brandName)
    })
}