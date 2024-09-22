package com.example.photosearcher

    import retrofit2.Call
    import retrofit2.http.GET
    import retrofit2.http.Query

    interface UnsplashApi {
    @GET("/search/photos")
        fun getPhotos(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String,
            @Query("query") query: String
        ): Call<PhotoModel>
    }
