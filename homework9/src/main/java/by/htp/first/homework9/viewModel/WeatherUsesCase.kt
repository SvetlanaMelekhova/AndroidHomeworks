package by.htp.first.homework9.viewModel

import by.htp.first.homework9.data.WeatherFromApi

class WeatherUsesCase {
    fun filterTemp(weatherData: WeatherFromApi): Boolean = weatherData.list[0].main.temp < 50.0
}