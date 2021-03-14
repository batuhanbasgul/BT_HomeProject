# BT_HomeProject

This project has developed for **BT Information Services** as a test project. Basically fetches data from [AccuWeather](https://developer.accuweather.com/apis) 
and saves it into local SQLite database and sets UI by SQLite.

# Modules:
  ## db.data_model
  
  **forecast** - Contains data classes for Forecast data that will be obtained from [AccuWeather](https://developer.accuweather.com/apis)  
  **geo_position** - Contains data classes for GeoPosition data that will be obtained from [AccuWeather](https://developer.accuweather.com/apis)  
  **Gps** - Data class that holds coordinates.  
  **User** - Data class that holds user account information.  
  
  ## db.service
  
  **api** - Contains Retrofit Builder and API Services Interface such as GET or POST methods.  
  **repository** - Contains repositories for datas that makes requestests.  
  **AuthenticationUtils** - Contains methods for user authentication by [Firebase](https://console.firebase.google.com)  
  **GpsUtils** - Contains methods such as checking if location service is permitted and getting coordinates.
  
  ## db.sqlite
  
  **DatabaseHelper** - Creates Database by using SQLiteOpenHelper interface.  
  **ForecastLogDao** - Contains methods for using forecast data from SQLite Database.  
  **propertiesLogDao** - Contains methods for using properties data from SQLite Database.  
    
  ## utils
  
  **Constants** - Object class for constant variables such as GPS provider ande API Url.  
  **Utils** - Contains various utils like converting value or getting current time period.  
  
  ## view
  
  **activity** - Contains activity classes.  
  **adapter** - Contains adapters for view.  
  
  ## view_model
  
  **ForecastViewModel** - Passes query variable(coordinates) and turns observable live data for to observe data.  
  **GeoPositionViewModel** - Passes query variable(lcoationKey) and turns observable live data for to observe data.  
  
# Model:
* Arhcitecture: MVVM
* Design Patterns: Singleton

# Permissions

* INTERNET
* ACCESS_FINE_LOCATION
* ACCESS_WIFI_STATE
* ACCESS_NETWORK_STATE

# Dependencies:

* Firebase Database  
```implementation 'com.google.firebase:firebase-database:19.7.0'```  
```implementation 'com.google.firebase:firebase-storage:19.2.1'```  
* Firebase Authentication  
```implementation 'com.google.firebase:firebase-auth:20.0.3'```  
* Retrofit2  
```implementation "com.squareup.retrofit2:retrofit:2.6.0"```  
```implementation "com.squareup.retrofit2:converter-gson:2.6.0"```  
* Lifecycle  
``` implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0' ```  
* Coroutines  
```implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1"```  
```implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1"```  
* Glide  
``` implementation 'com.github.bumptech.glide:glide:4.12.0' ```  
``` annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0' ```  
* SwipeRefreshLayout  
```implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"```  
* Multidex  
```implementation "com.android.support:multidex:1.0.3""```  

# Authors
* **Batuhan BASGUL** - [batuhanbasgul](https://github.com/batuhanbasgul?tab=repositories)
