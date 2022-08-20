package sebastian.company.min3rapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import sebastian.company.min3rapp.R

val gilroy = FontFamily(
    listOf(
        Font(
            R.font.gilroy_light, FontWeight.Normal),
        Font(
            R.font.gilroy_extrabold, FontWeight.ExtraBold),

    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    subtitle1 = TextStyle(
        color = Color.LightGray,
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    caption = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    h1 = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 22.sp
    ),
    h2 = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp
    ),

    h3 = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    h4 = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        color = Color.White,
        fontFamily = gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    )


    )