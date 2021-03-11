package by.htp.first.homework9.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.htp.first.homework9.OnChangeFragmentListener
import by.htp.first.homework9.R
import by.htp.first.homework9.database.Cities
import by.htp.first.homework9.databinding.FragmentCityChoiceBinding
import by.htp.first.homework9.databinding.ItemDialogBinding
import by.htp.first.homework9.util.Constants.WEATHER_FRAGMENT
import by.htp.first.homework9.util.Constants.actualCity
import by.htp.first.homework9.viewModel.WeatherViewModel

class CityChoiceFragment : Fragment(R.layout.fragment_city_choice) {

    private lateinit var binding: FragmentCityChoiceBinding
    private lateinit var localAdapter: CitiesAdapter
    private lateinit var onCityClickListener: CitiesAdapter.OnCityClickListener
    private var checkedView: ImageView? = null
    private lateinit var viewModelProvider: ViewModelProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityChoiceBinding.bind(view)
        viewModelProvider = ViewModelProvider(this)
        viewModelProvider.get(WeatherViewModel::class.java).also {
            it.citiesLaveData.observe(viewLifecycleOwner, { cityList -> localAdapter.addCity(cityList) })
            it.loadCityList(view.context)
        }

        onCityClickListener = object : CitiesAdapter.OnCityClickListener {
            override fun onCityClick(city: Cities, viewNeedToCheck: ImageView, isLongClick: Boolean) {
                if (!isLongClick) {
                    actualCity = city.city
                    checkedView?.visibility = View.INVISIBLE
                    checkedView = viewNeedToCheck
                } else {
                    viewModelProvider.get(WeatherViewModel::class.java).also {
                        it.citiesLaveData.observe(viewLifecycleOwner, { cityList -> localAdapter.addCity(cityList) })
                        it.deleteCityUpdateList(view.context, city)
                    }
                }
            }
        }

        localAdapter = CitiesAdapter(onCityClickListener)
        binding.recycler.apply {
            adapter = localAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.apply {
            addCityFAButton.setOnClickListener { getCityName(view) }
            backButton.setOnClickListener {
                (activity as OnChangeFragmentListener).onFragmentChange(WEATHER_FRAGMENT, null)
            }
        }
    }

    private fun getCityName(view: View) {
        val itemDialogBinding: ItemDialogBinding = ItemDialogBinding.inflate(LayoutInflater.from(view.context))
        AlertDialog.Builder(view.context).apply {
            setView(itemDialogBinding.root)
            setTitle(getString(R.string.enter_city_name))
            setPositiveButton(getString(R.string.ok_dialog_button)) { _, _ ->
                if (itemDialogBinding.cityNameEditText.text.toString().isNotEmpty()) {
                    viewModelProvider.get(WeatherViewModel::class.java).also {
                        it.citiesLaveData.observe(viewLifecycleOwner, { city -> localAdapter.addCity(city) })
                        it.addCityIntoDb(view.context, itemDialogBinding.cityNameEditText.text.toString())
                    }
                }
            }
            setNegativeButton(getString(R.string.cancle_dialog_button)) { dialogInterface, _ -> dialogInterface.cancel() }
            setCancelable(false)
            create()
            show()
        }
    }
}