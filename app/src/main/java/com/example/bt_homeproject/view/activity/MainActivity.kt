package com.example.bt_homeproject.view.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bt_homeproject.R
import com.example.bt_homeproject.db.data_model.Gps
import com.example.bt_homeproject.db.data_model.forecast.DailyForecast
import com.example.bt_homeproject.db.data_model.forecast.ForecastData
import com.example.bt_homeproject.db.data_model.geo_position.GeoPositionData
import com.example.bt_homeproject.db.data_model.log.ForecastLog
import com.example.bt_homeproject.db.data_model.log.PropertiesLog
import com.example.bt_homeproject.db.service.AuthenticationUtils.Companion.logOut
import com.example.bt_homeproject.db.service.GpsUtils
import com.example.bt_homeproject.db.sqlite.DatabaseHelper
import com.example.bt_homeproject.db.sqlite.ForecastLogDao
import com.example.bt_homeproject.db.sqlite.PropertiesLogDao
import com.example.bt_homeproject.view.adapter.ForecastAdapter
import com.example.bt_homeproject.view_model.ForecastViewModel
import com.example.bt_homeproject.view_model.GeoPositionViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //API
    private lateinit var geoPositionViewModel : GeoPositionViewModel
    private lateinit var forecastViewModel : ForecastViewModel
    private lateinit var geoPositionData : GeoPositionData
    private lateinit var forecastData: ForecastData
    //SQLite
    private lateinit var dbHelper:DatabaseHelper
    private lateinit var propertiesLogDao:PropertiesLogDao
    private lateinit var forecastLogDao:ForecastLogDao
    private lateinit var title:String
    private lateinit var subTitle:String
    //Adapter
    private lateinit var forecastAdapter:ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set UI elements default
        setUIByDefault()
        setUIElements()

        //Refresh
        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            setUIElements()
        })

    }

    private fun setUIByDefault(){
        toolbarMain.title = resources.getString(R.string.app_title)
        toolbarMain.subtitle = resources.getString(R.string.app_subtitle)
        setSupportActionBar(toolbarMain)
    }

    private fun setUIElements(){
        progressBarMain.visibility = View.VISIBLE
        if(isNetworkConnected()){
            getGeoPosition()
        }else{
            setUIFromSQLite()
            Toast.makeText(this,resources.getString(R.string.no_internet_fetched_sqlite),Toast.LENGTH_LONG).show()
        }
    }

    private fun getGeoPosition(){
        val gps: Gps? = GpsUtils.getCoordinates(this@MainActivity)
        geoPositionViewModel = ViewModelProvider(this@MainActivity).get(GeoPositionViewModel::class.java)
        geoPositionViewModel.geoPositionData.observe(this@MainActivity, Observer { geoPosition ->
            geoPositionData = geoPosition
            getForecast()
        })
        gps?.let {
            geoPositionViewModel.setQValue(
                    "${gps.latitude},${gps.longitude}"
            )
        }
    }

    private fun getForecast(){
        forecastViewModel = ViewModelProvider(this@MainActivity).get(ForecastViewModel::class.java)
        forecastViewModel.forecastData.observe(this@MainActivity, Observer { forecast ->
            forecastData = forecast
            updateSQLiteDataByService()
        })
        forecastViewModel.setLocationKeyValue(geoPositionData.Key)
    }

    private fun updateSQLiteDataByService(){

        if(!geoPositionData.AdministrativeArea.LocalizedName.isNullOrEmpty()){
            title=geoPositionData.AdministrativeArea.LocalizedName
        }else if(!geoPositionData.AdministrativeArea.EnglishName.isNullOrEmpty()){
            title = geoPositionData.AdministrativeArea.EnglishName
        }else{
            title = resources.getString(R.string.app_title)
        }

        if(!geoPositionData.LocalizedName.isNullOrEmpty()){
            subTitle = geoPositionData.LocalizedName
        }else if(!geoPositionData.EnglishName.isNullOrEmpty()){
            subTitle=geoPositionData.EnglishName
        }else{
            subTitle= resources.getString(R.string.app_subtitle)
        }

        dbHelper= DatabaseHelper(this@MainActivity)
        propertiesLogDao = PropertiesLogDao
        forecastLogDao = ForecastLogDao

        forecastLogDao.clearForecast(dbHelper)
        for(it:DailyForecast in forecastData.DailyForecasts){
            forecastLogDao.addForecast(dbHelper,
                    ForecastLog(it.Date,
                            it.Day.Icon,
                            it.Night.Icon,
                            it.Day.IconPhrase,
                            it.Night.IconPhrase,
                            it.Temperature.Maximum.Value,
                            it.Temperature.Minimum.Value))
        }

        propertiesLogDao.clearProperties(dbHelper)
        propertiesLogDao.addProperties(dbHelper,
                PropertiesLog(title,
                        subTitle,
                        forecastData.Headline.Text,
                        forecastData.Headline.MobileLink))

        setUIFromSQLite()
    }

    private fun setUIFromSQLite(){
        dbHelper = DatabaseHelper(this@MainActivity)

        //Setting title
        propertiesLogDao = PropertiesLogDao
        val propertiesLog:PropertiesLog = propertiesLogDao.getProperties(dbHelper)

        //Setting subtitle
        toolbarMain.title = propertiesLog.title
        toolbarMain.subtitle = propertiesLog.subTitle

        //Description
        textViewMainDescription.text = propertiesLog.description
        textViewMainDescription.visibility = View.VISIBLE

        //Link
        textViewAccuWeather.visibility = View.VISIBLE
        textViewAccuWeather.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(propertiesLog.link))
            startActivity(browserIntent)
        }

        forecastLogDao = ForecastLogDao
        val forecastList:ArrayList<ForecastLog> = forecastLogDao.getForecast(dbHelper)

        //Set Recyclerview Adapter
        forecastAdapter= ForecastAdapter(this@MainActivity,forecastList)
        recyclerViewMain.setHasFixedSize(true)
        recyclerViewMain.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerViewMain.adapter=forecastAdapter

        swipeRefreshLayout.isRefreshing = false
        progressBarMain.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout -> {
                logOut(this@MainActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        geoPositionViewModel.cancelJobs()
        forecastViewModel.cancelJobs()
    }

    @Suppress("DEPRECATION")
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}