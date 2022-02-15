package by.htp.first.homework9.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.htp.first.homework9.OnChangeFragmentListener
import by.htp.first.homework9.R
import by.htp.first.homework9.data.WeatherFromApi
import by.htp.first.homework9.databinding.FragmentWeatherBinding
import by.htp.first.homework9.util.Constants.CITY_CHOICE_FRAGMENT
import by.htp.first.homework9.util.Constants.actualCity
import by.htp.first.homework9.viewModel.WeatherViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModelProvider: ViewModelProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherBinding.bind(view)
        viewModelProvider = ViewModelProvider(this)
        viewModelProvider.get(WeatherViewModel::class.java).also {
            it.newsWeatherLaveData.observe(viewLifecycleOwner, { data -> showContent(data) })
            it.errorLiveData.observe(viewLifecycleOwner, { errorMsg -> showError(errorMsg) })
            it.tooMachDegreesLiveData.observe(viewLifecycleOwner, { useCase -> showError(useCase)})
            it.fetchWeather(actualCity)
        }

        binding.changeCountry.setOnClickListener {
            (activity as OnChangeFragmentListener).onFragmentChange(CITY_CHOICE_FRAGMENT, null)
        }
    }

    private fun showContent(weather: WeatherFromApi) {
        val dateRegex = "\\s*\\d{2}:\\d{2}:\\d{2}"
        binding.apply {
            countryName.text = weather.city.name

            currentData.text = weather.list[0].dt_txt
            degrees.text = weather.list[0].main.temp.toInt().toString()
            installImage(weather.list[0].weather[0].icon, binding.weatherImage)

            day2.text = weather.list[8].dt_txt.replace(Regex(dateRegex), "")
            degrees2.text = weather.list[8].main.temp.toInt().toString()
            clouds2.text = weather.list[8].weather[0].main
            installImage(weather.list[8].weather[0].icon, binding.imgDay2)

            day3.text = weather.list[16].dt_txt.replace(Regex(dateRegex), "")
            degrees3.text = weather.list[16].main.temp.toInt().toString()
            clouds3.text = weather.list[16].weather[0].main
            installImage(weather.list[16].weather[0].icon, binding.imgDay3)

            day4.text = weather.list[24].dt_txt.replace(Regex(dateRegex), "")
            degrees4.text = weather.list[24].main.temp.toInt().toString()
            clouds4.text = weather.list[24].weather[0].main
            installImage(weather.list[24].weather[0].icon, binding.imgDay4)

            day5.text = weather.list[32].dt_txt.replace(Regex(dateRegex), "")
            degrees5.text = weather.list[32].main.temp.toInt().toString()
            clouds5.text = weather.list[32].weather[0].main
            installImage(weather.list[32].weather[0].icon, binding.imgDay5)
        }
    }

    private fun showError(errorMessage: String) {
        when (errorMessage) {
            "Error: HTTP 404 Not Found" -> Snackbar.make(this.requireView(),
                getString(R.string.error_city_name),
                Snackbar.LENGTH_LONG).show()
            else -> Snackbar.make(this.requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun installImage(imageId: String, imageView: ImageView) {
        Glide.with(this)
            .load("https://openweathermap.org/img/w/$imageId.png")
            .error(R.drawable.ic_weather_pic)
            .into(imageView)
    }
}