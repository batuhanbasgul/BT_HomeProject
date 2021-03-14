package com.example.bt_homeproject.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bt_homeproject.db.data_model.forecast.ForecastData
import com.example.bt_homeproject.db.service.repository.ForecastRepository

class ForecastViewModel : ViewModel() {
    private val _locationKey: MutableLiveData<String> = MutableLiveData()

    val forecastData: LiveData<ForecastData> = Transformations.switchMap(_locationKey){
        ForecastRepository.getForecast(it)
    }

    fun setLocationKeyValue(locationKey:String){
        if(_locationKey.value == locationKey){
            return
        }
        _locationKey.value = locationKey
    }
    fun cancelJobs(){
        ForecastRepository.cancelJobs()
    }
}