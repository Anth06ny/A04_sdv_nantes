package com.example.a04_sdv_nantes.exo

import android.R.attr.data
import android.health.connect.datatypes.units.Length
import com.example.a04_sdv_nantes.exo.ThermometerEntity.Companion.getCelsiusThermometer
import java.util.Random

fun main() {



    val house = HouseEntity("rouge", 5,6)

    var t1 = ThermometerEntity(min=-20, max= 50, value =0)
    println("Température de ${t1.value}") // 0

    //Cas qui marche
    t1.value = 10
    println("Température de ${t1.value}") // 10 attendu

    //Borne minimum
    t1.value = -30
    println("Température de ${t1.value}") // -20 attendu

    //Borne maximum
    t1.value = 100
    println("Température de ${t1.value}") // 50 attendu

    //Pour les plus rapides : Cas de départ
    t1 = ThermometerEntity(min=-20, max= 50, value =-100)
    println("Température de ${t1.value}") // -20 attendu

    val t = ThermometerEntity.getCelsiusThermometer()

}


class RandomName {
    private val list = arrayListOf("Toto", "Tata", "Bob")
    private var old = ""

    fun add(name: String) {
        if (name.isNotBlank() && name !in list) {
            list += name
        }
    }

    fun addV2(name: String) = if (name.isNotBlank() && name !in list)  list.add(name) else false

    fun addAll(vararg nameList : String) {
        for(name  in nameList){
            add(name)
        }
    }

    fun addAllV2(vararg nameList : String) {
        nameList.forEach{ add(it)}
    }

    //version simple
    fun next() = list.random()

    //version pas 2 identique de suite
    fun nextDiff(): String {
        var send = next()
        while (send == old) {
            send = next()
        }
        old = send
        return send
    }


    fun nextDiffV3() : String = list.filter { it != old  }.random().also { old = it }










    //Idem sur une ligne
    //Also retourne l'objet sur lequel il est appelé
    fun nextDiffV2() = list.filter { it != old }.random().also { old = it }

    //Retourne 2 noms différents
    fun next2() = Pair(nextDiffV2(), nextDiffV2())
}


class ThermometerEntity(val min:Int, val max : Int,  value : Int){

    var value = value.coerceIn(min, max)
        set(newValue) {
            field = newValue.coerceIn(min, max)
        }

    companion object {
        fun getCelsiusThermometer() = ThermometerEntity(-30, 50, 0)
    }
}

class PrintRandomIntEntity(val max : Int){
    private val random : Random = Random()

    init {
        println(random.nextInt(max))
        println(random.nextInt(max))
        println(random.nextInt(max))
    }
}

class HouseEntity(var color : String, length: Int, width : Int) {
    var area = length * width
}

data class CarEntity(var marque: String = "",var model : String = "") {
    var couleur : String = ""
}

