package sebastian.company.min3rapp.ui.crypto_data

import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.domain.model.CryptoProject
import sebastian.company.min3rapp.ui.theme.*

@Composable
fun DataScreen(){
    
    
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.brand_darkblue))
        .verticalScroll(rememberScrollState())
        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .padding(vertical = 16.dp)
            ) {

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    colorResource(id = R.color.brand_darkblue_variant),
                    RoundedCornerShape(14.dp)
                )) {
                TextBox(text = "Crypto Market",
                    modifier = Modifier.weight(1f),
                    type = "title")
                BoldTextBox(text = "24.56%", modifier = Modifier.weight(1f),
                    direction = -1)
                Icon(painter = painterResource(id = R.drawable.ic_downvote),
                    contentDescription = "market change",
                    tint = Color.Red,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1f))
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(
                    colorResource(id = R.color.brand_darkblue_variant),
                    RoundedCornerShape(14.dp)
                )) {

                TextBox(text = "Coin Prices",
                    modifier = Modifier.weight(1f),
                    type = "title")
                Image(painter = painterResource(id = R.drawable.ic_coins_vector),
                    contentDescription = "market change",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1f))
                TextBox(text = "Daily tracker of top 100 coins",
                    modifier = Modifier.weight(1f),
                    type = "subtitle")


            }

        }

        Text(modifier = Modifier.padding(start = 12.dp),
            text = "Market Dominance",
            style = Typography.caption,
            fontWeight = FontWeight.ExtraBold)
        
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .background(
                colorResource(id = R.color.brand_darkblue_variant),
                RoundedCornerShape(14.dp)
            )
            .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {

            ProgressPieChart(points = listOf(105f, 35f, 40f, 5f),
                colors = listOf(PieChart1, PieChart2, PieChart3, PieChart4),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize())

            Spacer(modifier = Modifier.width(4.dp))

            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(
                    TransparentGrey,
                    RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0..3){
                        PieChartLegendItem(dataPoint = "54", color = PieChart1, coin = "Other")
                    }
                }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), 
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(modifier = Modifier.padding(start = 12.dp),
                text = "Projects",
                style = Typography.caption,
                fontWeight = FontWeight.ExtraBold)
            Text(modifier = Modifier.padding(end = 12.dp),
                text = "See All",
                style = Typography.caption,
                color = colorResource(id = R.color.brand_pink),
                fontWeight = FontWeight.Normal)
        }
        


        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.75f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CryptoProjectItem(project = CryptoProject("Internet Computer", "ICP",
                    "2009", "", ""),
                    modifier = Modifier.weight(1f))

                CryptoProjectItem(project = CryptoProject("Internet Computer", "ICP",
                    "2009", "", ""),
                    modifier = Modifier.weight(1f))

            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.75f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CryptoProjectItem(project = CryptoProject("Internet Computer", "ICP",
                    "2009", "", ""),
                    modifier = Modifier.weight(1f))

                CryptoProjectItem(project = CryptoProject("Internet Computer", "ICP",
                    "2009", "", ""),
                    modifier = Modifier.weight(1f))

            }
        }





    }
    

}


@Composable
fun TextBox(modifier: Modifier = Modifier, text: String, type: String){
    Box(modifier = modifier.then(Modifier.fillMaxSize())){
        Text(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
            .padding(horizontal = 16.dp),
            style = if(type == "title") Typography.caption else Typography.subtitle1,
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = if (type == "title") FontWeight.ExtraBold else FontWeight.Normal)
    }
}


@Composable
fun BoldTextBox(modifier: Modifier = Modifier, text: String, direction: Int){
    Box(modifier = modifier.then(Modifier.fillMaxSize())){
        Text(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
            text = text,
            textAlign = TextAlign.Center,
            style = Typography.h2,
            color = if (direction == -1) Color.Red else Color.Green
            )
    }
}


@Composable
fun PieChartLegendItem(dataPoint: String, color: Color, coin: String){
    Row(modifier = Modifier
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween) {

        Text(modifier = Modifier.weight(3f),
            text = "$dataPoint%",
            textAlign = TextAlign.Center,
            style = Typography.body1,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1)
        Spacer(modifier = Modifier.width(8.dp))
        Box(modifier = Modifier
            .weight(1f)
            .height(8.dp)
            .background(color = color, RoundedCornerShape(12.dp)))
        Spacer(modifier = Modifier.width(8.dp))
        Text(modifier = Modifier.weight(3f),
            text = coin,
            textAlign = TextAlign.Center,
            style = Typography.body1,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1)

    }

}

@Composable
fun CryptoProjectItem(project: CryptoProject, modifier: Modifier = Modifier){
    Column(modifier = modifier
        .then(Modifier.fillMaxSize())
        .background(
            colorResource(id = R.color.brand_darkblue_variant),
            RoundedCornerShape(14.dp)
        )
        .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween) {

        Column{
            Text(text = "${project.name} - ${project.ticker}",
                style = Typography.body1,
                fontWeight = FontWeight.ExtraBold)
            Text(text = project.dateFounded,
                style = Typography.subtitle1,
                fontWeight = FontWeight.Normal)
        }



        Column{
            Row(modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_darkblue),
                    RoundedCornerShape(12.dp)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Icon(painter = painterResource(id = R.drawable.ic_web),
                    contentDescription = "project link",
                    tint = Color.White)

                Text(modifier = Modifier.padding(start = 8.dp),
                    text = "Website",
                    style = Typography.subtitle1,
                    fontWeight = FontWeight.Normal)

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier
                .background(
                    colorResource(id = R.color.brand_darkblue),
                    RoundedCornerShape(12.dp)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {

                Icon(painter = painterResource(id = R.drawable.ic_article),
                    contentDescription = "project link",
                    tint = Color.White)

                Text(modifier = Modifier.padding(start = 8.dp),
                    text = "Whitepaper",
                    style = Typography.subtitle1,
                    fontWeight = FontWeight.Normal)

            }
        }


    }
}

@Preview
@Composable
fun DataScreenTest(){
    DataScreen()
}

@Preview
@Composable
fun LegendTest(){
    PieChartLegendItem(dataPoint = "54.2", color = PieChart1, coin = "BTC")
}

@Preview
@Composable
fun ProjectTest(){
    CryptoProjectItem(project = CryptoProject("Internet Computer", "ICP",
    "2009", "", ""))
}