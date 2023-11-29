package com.example.ch09.network

import com.example.ch09.BuildConfig
import com.example.ch09.network.api.AirQualityApi
import com.example.ch09.network.api.KaKaoLocationApi
import com.example.ch09.network.models.airquality.MeasuredValue
import com.example.ch09.network.models.monitoringstation.MonitoringStation
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repository {

     suspend fun getNearMonitoringStation(latitude: Double, longitude: Double): MonitoringStation?{
        val tmCoordinates = kakaoLocationApi.getTmCoordinates(longitude, latitude)
            .body()?.documents?.firstOrNull()

        val tmX = tmCoordinates?.x
        val tmY = tmCoordinates?.y

        return  airQualityApi.getNearbyMonitoringStation(tmX!!,tmY!!)
            .body()?.response?.body?.monitoringStations?.minByOrNull {
                it.tm ?:Double.MAX_VALUE
            }
    }

    suspend fun getLatestAirQualityData(stationName: String): MeasuredValue ?
    = airQualityApi.getRealtimeAirQuality(stationName).body()?.response?.body?.measuredValues?.firstOrNull()

    private val kakaoLocationApi: KaKaoLocationApi by lazy {
        Retrofit.Builder()
            .baseUrl(Url.KAKAO_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }

    private val airQualityApi : AirQualityApi by lazy {
        Retrofit.Builder()
            .baseUrl(Url.AIR_QUALITY_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildHttpClient())
            .build()
            .create()
    }


    private fun buildHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG){
                        HttpLoggingInterceptor.Level.BODY
                    }else{
                        //배포할땐 로깅 안보이게
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}