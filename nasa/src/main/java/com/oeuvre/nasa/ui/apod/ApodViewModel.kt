package com.oeuvre.nasa.ui.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oeuvre.nasa.model.Apod
import com.oeuvre.nasa.repositories.ApodRepository


class ApodViewModel : ViewModel() {

	private var mutableLiveData: MutableLiveData<Apod?>? = null
	private var apodRepository: ApodRepository? = null



	fun init() {
		if (mutableLiveData != null) {
			return
		}
		apodRepository = ApodRepository.getInstance()
		mutableLiveData = apodRepository?.getPlanetaryPictureOfTheDay("DEMO_KEY")
	}



	fun getNewsRepository(): LiveData<Apod?>? {
		return mutableLiveData
	}
}