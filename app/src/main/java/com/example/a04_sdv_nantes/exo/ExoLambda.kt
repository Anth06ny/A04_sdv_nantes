package com.example.a04_sdv_nantes.exo

fun main() {
    exo1()
}

fun exo1() {
    //Déclaration
    val lower: (String) -> Unit = { it: String -> println(it.lowercase()) }
    val lower2 = { it: String -> println(it.lowercase()) }
    val lower3: (String) -> Unit = { it -> println(it.lowercase()) }
    val lower4: (String) -> Unit = { println(it.lowercase()) }

    //Appel
    lower("Coucou")

    val hour :(Int)->Int = {it/60}
    println(hour(123))

    val max :(Int, Int)->Int = {a,b -> Math.max(a,b ) }
    val reverse = {it:String -> it.reversed()}

    var minToMinHour : ((Int?)-> Pair<Int, Int>?)? = { if(it == null) null else  Pair(it/60, it%60)}
    //val minToMinHour : (Int?)-> Pair<Int, Int>? = { it?.let { Pair(it/60, it%60) }}
    println(minToMinHour?.invoke(123))
    println(minToMinHour?.invoke(null))
    minToMinHour = null



}