package com.example.bt_homeproject.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.bt_homeproject.db.data_model.geo_position.GeoPositionData
import com.example.bt_homeproject.db.service.repository.GeoPositionRepository

class GeoPositionViewModel : ViewModel(){
    private val _q:MutableLiveData<String> = MutableLiveData()

    val geoPositionData:LiveData<GeoPositionData> = Transformations.switchMap(_q){
        GeoPositionRepository.getGeoPosition(it)                                                               //switchMap triggers when Geo data changes
    }

    fun setQValue(q:String){
        if(_q.value == q){
            return
        }
        _q.value = q
    }
    fun cancelJobs(){
        GeoPositionRepository.cancelJobs()
    }
}