package sebastian.company.min3rapp.ui.crypto_data

import android.widget.ProgressBar
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ProgressPieChart(
    points: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier
){

    val total = points.sum()

    val proportions = points.map {
        it / total * 100
    }

    val sweepAnglePercentage = proportions.map{
        //should just be proportion * 360 as they will already be decimals
        360 * it / 100
    }

    Canvas(modifier = modifier.then(Modifier
        .fillMaxSize()
        .background(Color.Transparent)
        )
    ){

        var startAngle = 270f
        sweepAnglePercentage.forEachIndexed { index, sweepAngle ->
            DrawArc(
                colors[index],
                startAngle = startAngle,
                sweepAngle
            )

            startAngle += sweepAngle
        }


    }

}


fun DrawScope.DrawArc(
    color: Color,
    startAngle: Float,
    sweepAngle: Float
){

    val padding = 200f
    val arcSize = size.width/1.5f
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        size = Size(arcSize, arcSize),
        topLeft = Offset((size.width-arcSize)/2f, (size.height-arcSize)/2f),
        useCenter = false,
        style = Stroke(
            width = arcSize/5f
        ),

    )
}


@Preview
@Composable
fun TestPieChart(){
    ProgressPieChart(points = listOf(105f, 35f, 40f, 5f, 60f),
        colors = listOf(Color.Green, Color.Blue, Color.Magenta, Color.Black, Color.Yellow))
}