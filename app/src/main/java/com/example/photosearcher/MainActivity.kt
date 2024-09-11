package com.example.photosearcher

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.photosearcher.ui.theme.PhotoSearcherTheme
import com.squareup.picasso.Picasso


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.imageView)
        Picasso.get()
            .load(
                "imageId"
            )
            .placeholder(R.drawable.circle)
            .resize(50, 50)
            .into(imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    //set animations here

                }

                override fun onError(e: java.lang.Exception?) {
                    //do smth when there is picture loading error
                    Log.d("error", e.toString())
                }
            })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoSearcherTheme {
        Greeting("Android")
    }
}