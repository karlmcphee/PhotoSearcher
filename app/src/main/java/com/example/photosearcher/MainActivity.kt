package com.example.photosearcher

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
                RetrofitInstance.api.getPhotos(1, 25, key, query) //The number of images can be changed here
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
                            } else {
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
        searchText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            Log.d("first", "first")
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
              Log.d("second", "second")
                searchButton.performClick();
            }
            false
        })
    }
}