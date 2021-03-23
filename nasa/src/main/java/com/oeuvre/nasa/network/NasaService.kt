package com.oeuvre.nasa.network



import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaService {


	private val BASE_URL = "https://api.nasa.gov"
	private var retrofit: Retrofit? = null


	fun getClient(): Retrofit {

		if (retrofit == null) {

			val client = OkHttpClient.Builder()
					.build()

			retrofit = Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.client(client)
					.build()
		}

		return retrofit as Retrofit
	}

}