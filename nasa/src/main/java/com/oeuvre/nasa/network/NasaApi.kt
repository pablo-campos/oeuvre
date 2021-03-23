package com.oeuvre.nasa.network

import com.oeuvre.nasa.model.Apod
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

	@GET("/planetary/apod")
	fun getPlanetaryPictureOfTheDay(@Query("api_key") apiKey: String): Call<Apod>
}