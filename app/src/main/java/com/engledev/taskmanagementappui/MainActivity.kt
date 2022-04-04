package com.engledev.taskmanagementappui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.engledev.taskmanagementappui.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementAppUITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

                    Column {
                        HeaderUI()
                        TaskCard()
                        StatisticUI()
                        DescriptionUI()
                    }
                }
            }
        }
    }

}

@Composable
fun DescriptionUI() {
    Column(modifier = Modifier.padding(horizontal = 30.dp)) {
        Text(
            text = "Description",
            fontSize = 18.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it",
            fontSize = 14.sp,
            color = ColorSecondaryText,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun StatisticUI() {
    Column(modifier = Modifier.padding(30.dp)) {
        Text(
            text = "Jetpack Compose UI Design",
            fontSize = 18.sp,
            color = ColorPrimaryText,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = "",
                    tint = Color(0xFF818181),
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "09:00 AM - 11:00 AM",
                    fontSize = 14.sp,
                    color = Color(0xFF818181),
                    fontWeight = FontWeight.Medium
                )
            }

            Box(
                modifier = Modifier
                    .clip(Shapes.large)
                    .background(color = Color(0xFFDDE3FF))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "On Going",
                    fontSize = 12.sp,
                    color = ColorPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Statistic",
            fontSize = 18.sp,
            color = ColorSecondaryText,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            StatisticProgressUI()
            Spacer(modifier = Modifier.width(12.dp))
            StatisticIndicatorUI()
        }
    }
}

@Composable
fun StatisticIndicatorUI() {
    Column(
        modifier = Modifier.height(120.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        IndicatorItemUI(text = "Finish on time")
        IndicatorItemUI(text = "Past the deadline", color = ColorSecondary)
        IndicatorItemUI(text = "Still Ongoing", color = Color(0xFFE3E5E7))
    }
}

@Composable
fun IndicatorItemUI(text: String, color: Color = ColorPrimary) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = "",
            tint = color,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFF818181),
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun StatisticProgressUI(primaryProgress: Float = 60f, secondaryProgress: Float = 15f) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Center
    ) {
        Canvas(modifier = Modifier.size(100.dp)) {
            drawCircle(
                brush = SolidColor(
                    Color(0xFFE3E5E7)
                ),
                radius = size.width / 2,
                style = Stroke(34f)
            )

            val convertedPrimaryValue = (primaryProgress / 100) * 360
            val convertedSecondaryValue = ((secondaryProgress / 100) * 360) + convertedPrimaryValue

            drawArc(
                brush = SolidColor(ColorSecondary),
                startAngle = -90f,
                sweepAngle = convertedSecondaryValue,
                useCenter = false,
                style = Stroke(34f, cap = StrokeCap.Round)
            )

            drawArc(
                brush = SolidColor(ColorPrimary),
                startAngle = -90f,
                sweepAngle = convertedPrimaryValue,
                useCenter = false,
                style = Stroke(34f, cap = StrokeCap.Round)
            )

        }

        val annotatedString =
            AnnotatedString.Builder("${(primaryProgress + secondaryProgress).toInt()}%\nDone")
                .apply {
                    addStyle(
                        SpanStyle(
                            color = ColorSecondaryText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        ), 4, 8
                    )
                }

        Text(
            text = annotatedString.toAnnotatedString(),
            color = ColorPrimaryText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TaskCard() {
    val annotatedString = AnnotatedString.Builder("4/6 Task")
        .apply {
            addStyle(
                SpanStyle(
                    color = ColorPrimary
                ), 0, 3
            )
        }
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 30.dp),
        elevation = 0.dp, shape = Shapes.large
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Daily Tasks",
                    fontSize = 14.sp,
                    color = ColorSecondaryText,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_tick_circle),
                        contentDescription = "",
                        tint = ColorPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = annotatedString.toAnnotatedString(),
                        fontSize = 18.sp,
                        color = ColorPrimaryText,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Almost finished \nkeep it up",
                    color = Color(0xFF292D32),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .clip(Shapes.large),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ColorPrimary
                    ),
                    contentPadding = PaddingValues(
                        vertical = 0.dp,
                        horizontal = 16.dp
                    )
                ) {
                    Text(
                        text = "Daily Task",
                        fontSize = 12.sp,
                        modifier = Modifier.align(alignment = CenterVertically),
                        color = Color.White
                    )
                }
            }
            ProgressBarUI(progress = 80f)
        }
    }
}

@Composable
fun ProgressBarUI(progress: Float) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(6.dp)
        ) {
            drawCircle(
                brush = SolidColor(Color(0xFFE3E5E7)),
                radius = size.width / 2,
                style = Stroke(26f)
            )
            val converted = (progress / 100) * 360
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(
                        ColorSecondary,
                        ColorPrimary
                    )
                ),
                startAngle = -90f,
                sweepAngle = converted,
                useCenter = false,
                style = Stroke(26f, cap = StrokeCap.Round)
            )
        }

        val annotatedProgressString = AnnotatedString.Builder("${progress.toInt()}%\nDone").apply {
            addStyle(
                SpanStyle(
                    color = ColorSecondaryText,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Normal
                ), 4, 8
            )
        }

        Text(
            text = annotatedProgressString.toAnnotatedString(),
            fontSize = 14.sp,
            color = ColorPrimaryText,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun HeaderUI() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "Hello, Engle Dev!",
                fontSize = 18.sp,
                color = ColorPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Let's finish your today's tasks",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ColorSecondaryText
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_engle_dev),
            contentDescription = "",
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskManagementAppUITheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HeaderUI()
            TaskCard()
        }
    }
}