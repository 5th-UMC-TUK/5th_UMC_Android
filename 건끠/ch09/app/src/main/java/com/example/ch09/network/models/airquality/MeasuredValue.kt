package com.example.ch09.network.models.airquality


import com.google.gson.annotations.SerializedName

data class MeasuredValue(
    @SerializedName("coFlag")
    val coFlag: Any?,
    @SerializedName("coGrade")
    val coString: String?,
    @SerializedName("coValue")
    val coValue: String?,
    @SerializedName("dataTime")
    val dataTime: String?,
    @SerializedName("khaiGrade")
    val khaiString: String?,
    @SerializedName("khaiValue")
    val khaiValue: String?,
    @SerializedName("mangName")
    val mangName: String?,
    @SerializedName("no2Flag")
    val no2Flag: Any?,
    @SerializedName("no2Grade")
    val no2String: String?,
    @SerializedName("no2Value")
    val no2Value: String?,
    @SerializedName("o3Flag")
    val o3Flag: Any?,
    @SerializedName("o3Grade")
    val o3String: String?,
    @SerializedName("o3Value")
    val o3Value: String?,
    @SerializedName("pm10Flag")
    val pm10Flag: Any?,
    @SerializedName("pm10Grade")
    val pm10String: String?,
    @SerializedName("pm10String1h")
    val pm10String1h: String?,
    @SerializedName("pm10Value")
    val pm10Value: String?,
    @SerializedName("pm10Value24")
    val pm10Value24: String?,
    @SerializedName("pm25Flag")
    val pm25Flag: Any?,
    @SerializedName("pm25Grade")
    val pm25String: String?,
    @SerializedName("pm25Grade1h")
    val pm25String1h: String?,
    @SerializedName("pm25Value")
    val pm25Value: String?,
    @SerializedName("pm25Value24")
    val pm25Value24: String?,
    @SerializedName("so2Flag")
    val so2Flag: Any?,
    @SerializedName("so2Grade")
    val so2String: String?,
    @SerializedName("so2Value")
    val so2Value: String?
)