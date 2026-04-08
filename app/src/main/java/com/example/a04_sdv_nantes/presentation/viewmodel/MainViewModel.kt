package com.example.a04_sdv_nantes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_sdv_nantes.data.remote.KtorWeatherApi
import com.example.a04_sdv_nantes.data.remote.WeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Paris")


    while (viewModel.runInProgress.value) {
        println("Attente ...")
        Thread.sleep(500)
    }

    println("List : ${viewModel.dataList.value}")
    println("ErrorMessage : ${viewModel.errorMessage.value}" )

    //Pour que le programme s'arrête, inutile sur Android
    KtorWeatherApi.close()
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<WeatherEntity>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    fun loadWeathers(cityName: String) {

        runInProgress.value = true
        errorMessage.value = ""

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (cityName.length < 3) {
                    throw Exception("Il faut au moins 3 caractères")
                }

//Chercher les données
                dataList.value = KtorWeatherApi.loadWeathers(cityName)

            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = e.message ?: "Une erreur générique"
            } finally {
                runInProgress.value = false
            }
        }
    }
}