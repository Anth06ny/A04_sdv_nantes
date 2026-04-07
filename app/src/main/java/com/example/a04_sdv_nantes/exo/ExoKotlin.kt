package com.example.a04_sdv_nantes.exo

import android.R.attr.text
import com.example.a04_sdv_nantes.exo.Constants2.Companion.PRICE_BAGUETTE
import kotlin.random.Random

var v2: String? = "coucou"

fun main() {
    var v1 = "coucou"
    var v3: String? = null

    println(v1.uppercase())

    val v2Temp = v2
    if(v2Temp != null) {
        println(v2Temp.uppercase())
    }
    else {
        println(null)
    }

    println(v2?.uppercase())




    println(v3?.uppercase())

    var v4: Int? = null
//Laisser le curseur de la souris sur Random pour qu'il vous propose de l'importer
//Choisir celui de Koltin
    if (Random.nextBoolean()) {
        v4 = Random.nextInt(10)
    }
    println(v4 ?: "Pas de valeur")

    boulangerie(0,1,2)
    boulangerie( nbSand = 2, nbBag = 3)

}

fun boulangerie(nbCroi:Int = 0, nbBag:Int =0, nbSand:Int =0)
    = nbCroi * PRICE_CROISSANT + nbBag * PRICE_BAGUETTE + nbSand *  PRICE_SANDWITCH



fun String?.myIsNullOrBlank() : Boolean {
    if(this == null )
        return false
    else
        return this.isNotBlank()
}