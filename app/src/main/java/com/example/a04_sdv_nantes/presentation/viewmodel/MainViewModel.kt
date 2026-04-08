package com.example.a04_sdv_nantes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a04_sdv_nantes.data.remote.KtorWeatherApi
import com.example.a04_sdv_nantes.data.remote.WeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun main(){
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")


    while(viewModel.runInProgress.value) {
        println("Attente ...")
        Thread.sleep(500)
    }

    println("List : ${viewModel.dataList.value}" )

    //Pour que le programme s'arrête, inutile sur Android
    KtorWeatherApi.close()
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<WeatherEntity>())
    val runInProgress = MutableStateFlow(false)

    fun loadWeathers(cityName:String){

        runInProgress.value = true

        viewModelScope.launch(Dispatchers.IO) {
//Chercher les données
            dataList.value = KtorWeatherApi.loadWeathers(cityName)

            runInProgress.value = false
        }
    }
}