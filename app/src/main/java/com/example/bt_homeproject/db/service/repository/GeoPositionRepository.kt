package com.example.bt_homeproject.db.service.repository

import androidx.lifecycle.LiveData
import com.example.bt_homeproject.db.data_model.geo_position.GeoPositionData
import com.example.bt_homeproject.db.service.api.RetrofitBuilder
import com.example.bt_homeproject.utils.Constants.apiKey
import com.example.bt_homeproject.utils.Constants.details
import com.example.bt_homeproject.utils.Constants.language
import com.example.bt_homeproject.utils.Constants.toplevel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object GeoPositionRepository {
    var job : CompletableJob? = null
    fun getGeoPosition(q:String):LiveData<GeoPositionData>{
        job = Job()
        return object : LiveData<GeoPositionData>(){
            override fun onActive() {
                super.onActive()
                job?.let {
                    CoroutineScope(IO + it).launch {
                        val geoPositionData = RetrofitBuilder.apiService.getGeo(q,apiKey,language,toplevel,details)
                        withContext(Main){
                            value = geoPositionData                                                 //Can not set live data on background
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