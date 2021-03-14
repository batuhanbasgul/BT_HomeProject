package com.example.bt_homeproject.db.service.repository

import androidx.lifecycle.LiveData
import com.example.bt_homeproject.db.data_model.forecast.ForecastData
import com.example.bt_homeproject.db.service.api.RetrofitBuilder
import com.example.bt_homeproject.utils.Constants
import kotlinx.coroutines.*

object ForecastRepository {
    var job : CompletableJob? = null
    fun getForecast(locationKey:String): LiveData<ForecastData> {
        job = Job()
        return object : LiveData<ForecastData>(){
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(Dispatchers.IO + it).launch {
                        val forecast = RetrofitBuilder.apiService.getForecast(locationKey,
                            Constants.apiKey,
                            Constants.language,
                            Constants.toplevel,
                            Constants.details
                        )
                        withContext(Dispatchers.Main){
                            value = forecast                                                 //Can not set live data on background
                            it.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }
}