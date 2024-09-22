package com.example.photosearcher

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.photosearcher.ui.theme.PhotoSearcherTheme
import com.squareup.picasso.BuildConfig
import retrofit2.Call


class MainActivity : ComponentActivity() {

    val pictureList = ArrayList<String>()
    lateinit var key: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchText = findViewById<EditText>(R.id.searchText)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        val customAdapter = Adapter(pictureList)
        recyclerView.adapter = customAdapter

        val applicationInfo = applicationContext.packageManager.getApplicationInfo(
            applicationContext.packageName, PackageManager.GET_META_DATA
        )
        try {
            val applicationInfo = applicationContext.packageManager.getApplicationInfo(
                applicationContext.packageName, PackageManager.GET_META_DATA
            )
            val bundle = applicationInfo.metaData
            key = (bundle.getString("keyValue") ?: "")

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        searchButton.setOnClickListener {
            var query = searchText.getText().toString()
            if(query != "") {
                searchText.setText("")
                RetrofitInstance.api.getPhotos(1, 10, key, query) //The number of images be set higher
                    .enqueue(object : retrofit2.Callback<PhotoModel> {
                        override fun onResponse(
                            call: Call<PhotoModel>,
                            response: retrofit2.Response<PhotoModel>
                        ) {
                            if (response.isSuccessful) {
                                pictureList.clear()
                                val photos = response.body()
                                val arrlist = photos?.results
                                for (picture in arrlist!!) {
                                    pictureList.add(picture.urls.small)
                                }
                                customAdapter.updateData(pictureList)
                                //Log.d("photos", photos.toString())
                                // Handle the photos, e.g., display them in a RecyclerView
                            } else {
                                // Handle the error
                                println("Error: ${response.code()} - ${response.message()}")
                                Log.d("error", "API access/conversion error")
                            }
                        }
                        override fun onFailure(call: Call<PhotoModel>, t: Throwable) {
                            Log.d("error", "API availability error")
                        }
            })
        }
        }
    }
}