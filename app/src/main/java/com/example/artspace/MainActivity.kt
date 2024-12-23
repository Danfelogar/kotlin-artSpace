package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    ArtSpaceLayout(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {

    var numberArt by remember { mutableIntStateOf(1) }

    val currentArt = when(numberArt) {
        1 -> R.drawable.art1
        2-> R.drawable.art2
        else -> R.drawable.art3
    }

    val currentTitle = when(numberArt) {
        1 -> R.string.art1_title
        2-> R.string.art2_title
        else -> R.string.art3_title
    }

    val currentArtist = when(numberArt) {
        1 -> R.string.art1_artist
        2-> R.string.art2_artist
        else -> R.string.art3_artist
    }

    val currentMade = when(numberArt) {
        1 -> R.string.art1_made
        2-> R.string.art2_made
        else -> R.string.art3_made
    }

//    var changeNextArt = changeNumArt(numberArt) {next -> numberArt = next }
//    var changePreviousArt = changeNumArt(numberArt, false){ prev -> numberArt = prev }

    Column(
        modifier = modifier
//            .statusBarsPadding()
//            .safeDrawingPadding()
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        ArtPictureCard(currentArt,currentTitle)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp))
        InfoArtCard(
            title = currentTitle,
            artist = currentArtist,
            madeDate = currentMade
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp))
        BtnActions(
            changeNextArt = { changeNumArt(numberArt, true) { next -> numberArt = next } },
            changePrevArt = { changeNumArt(numberArt, false) { prev -> numberArt = prev } }
        )
    }
}

fun changeNumArt(step: Int, isIncreasing: Boolean = true, onStepChanged: (Int) -> Unit) {
    val nextStep = (step + if (isIncreasing) 1 else -1).let {
        if (it > 3) 1 else if (it < 1) 3 else it
    }
    onStepChanged(nextStep)
}

@Composable
fun ArtPictureCard(
    @DrawableRes picture: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier
) {

    ElevatedCard(
        modifier = modifier
            .widthIn(max = 370.dp)
            .height(450.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ){
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =  Arrangement.Center
        ) {
            Image(
                painter = painterResource(picture),
                contentDescription = stringResource(description),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun InfoArtCard(
    @StringRes title: Int,
    @StringRes artist: Int,
    @StringRes madeDate: Int,
    modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
//            .fillMaxWidth()
            .widthIn(max = 370.dp)
            .height(130.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFECEBEF)
        )
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,

        ) {
            Text(
                text = stringResource(title),
                fontWeight =  FontWeight.W200,
                fontSize =  29.sp,
                lineHeight = 32.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = stringResource(artist),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = " (${stringResource(madeDate)})",
                    fontWeight =  FontWeight.W300,
                    fontSize =  16.sp
                )
            }

        }
    }
}

@Composable
fun BtnActions(
    changeNextArt: () -> Unit,
    changePrevArt: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            modifier = Modifier
                .width(160.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF535C77)
            ),
            onClick = { changePrevArt() }
        ) {
            Text(
                text = "Previous",
                fontSize = 17.sp,
                color = Color(0xFFF5F5F7)
            )
        }
        Button(
            modifier = Modifier
                .width(160.dp)
                .padding(10.dp),
            shape = RoundedCornerShape(45.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF535C77)
            ),
            onClick = { changeNextArt() }
        ) {
            Text(
                text = "Next",
                fontSize = 17.sp,
                color = Color(0xFFF5F5F7)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}