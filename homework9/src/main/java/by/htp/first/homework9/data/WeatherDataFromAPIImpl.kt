package by.htp.first.homework9.data

import io.reactivex.Single

interface WeatherDataFromAPIImpl {
    fun getWeather(country: String, units: String, appKey: String): Single<WeatherFromApi>
}