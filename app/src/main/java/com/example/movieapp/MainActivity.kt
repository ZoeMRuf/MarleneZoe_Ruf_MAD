package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.navigation.Navigation
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Navigation()
            }
        }
        //Task 1
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieAppTheme {
        Navigation()
    }
}




/* Example for Image:

Image(
painter = painterResource(id = R.drawable.wakanda_img),
contentDescription = null,
contentScale = ContentScale.Crop,
modifier = Modifier
    .fillMaxWidth()
    .clip(RoundedCornerShape(roundCorner, roundCorner))
)

*/

