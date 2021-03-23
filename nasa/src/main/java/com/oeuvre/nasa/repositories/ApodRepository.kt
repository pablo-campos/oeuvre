package com.oeuvre.nasa.repositories

import androidx.lifecycle.MutableLiveData
import com.oeuvre.nasa.model.Apod
import com.oeuvre.nasa.network.NasaApi
import com.oeuvre.nasa.network.NasaService.getClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApodRepository() {


	private val nasaApi: NasaApi = getClient().create(NasaApi::class.java)



	fun getPlanetaryPictureOfTheDay(apiKey: String?): MutableLiveData<Apod?> {
		val apod = MutableLiveData<Apod?>()
		nasaApi.getPlanetaryPictureOfTheDay(apiKey!!).enqueue(object : Callback<Apod?> {
			override fun onResponse(call: Call<Apod?>, response: Response<Apod?>) {
				if (response.isSuccessful) {
					apod.value = response.body()
				}
			}

			override fun onFailure(call: Call<Apod?>, t: Throwable) {
				apod.value = null
			}
		})
		return apod
	}



	companion object {
		private var apodRepository: ApodRepository? = null
		fun getInstance(): ApodRepository? {
			if (apodRepository == null) {
				apodRepository = ApodRepository()
			}
			return apodRepository
		}
	}


}