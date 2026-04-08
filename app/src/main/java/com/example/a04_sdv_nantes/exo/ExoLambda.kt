package com.example.a04_sdv_nantes.exo

import android.R.attr.action
import android.R.attr.data
import android.R.attr.value
import com.example.a04_sdv_nantes.data.remote.WindEntity


class MyLiveData<T>(value:T) {

    var value:T = value
        set(newValue) {
            field = newValue
            action(newValue)
        }

    var action : (T)->Unit = {}
}

fun main() {
    //exo3()

    var data = MyLiveData(WindEntity(5.0))

    data.action = {
        println(it)
    }
    data.value = data.value.copy(speed = data.value.speed + 1)

}

data class PersonEntity(var name: String, var note: Int)

fun exo3() {
    val list = arrayListOf(
        PersonEntity("toto", 16),
        PersonEntity("Tata", 20),
        PersonEntity("Toto", 8),
        PersonEntity("Charles", 14)
    )

    println("Afficher la sous liste de personne ayant 10 et +")
    //println(list.filter { it.note >=10 })
    //Pour un affichage de notre choix
    println(list.filter { it.note >= 10 }.joinToString("\n") { "-${it.name} : ${it.note}" })

    //TODO
    var isToto = { it: PersonEntity -> it.name.equals("toto", true) }


    println("\n\nAfficher combien il y a de Toto dans la classe ?")
    val nb = list.count { it.name.equals("toto", true) }
    val nb2 = list.count { isToto(it) }
    val nb3 = list.count(isToto)
    println("nb = $nb")

    println("\n\nAfficher combien de Toto ayant la moyenne (10 et +)")
    list.count { it.name.equals("toto", true) && it.note >= 10 }

    println("\n\nAfficher combien de Toto ont plus que la moyenne de la classe")
    val classAverage = list.map { it.note }.average()
    list.count { it.name.equals("toto", true) && it.note >= classAverage }

    println("\n\nAfficher la list triée par nom sans doublon")
    println("\n\nAjouter un point a ceux n’ayant pas la moyenne (<10)")
    list.filter { it.note < 10 }.forEach { it.note++ }

    println("\n\nAjouter un point à tous les Toto")

    println("\n\nRetirer de la liste ceux ayant la note la plus petite. (Il peut y en avoir plusieurs)")
    val minNote = list.minOf { it.note }
    list.removeIf { it.note == minNote }

    println("\n\nAfficher les noms de ceux ayant la moyenne(10et+) par ordre alphabétique")

    //TODO Créer une variable isToto contenant une lambda qui teste si un PersonEntity s'appelle Toto


    println("\n\nDupliquer la liste ainsi que tous les utilisateurs (nouvelle instance) qu'elle contient")
    println("\n\nAfficher par notes croissantes les élèves ayant eu cette note comme sur l'exemple")
}

fun exo1() {


    //Déclaration
    val lower: (String) -> Unit = { it: String -> println(it.lowercase()) }
    val lower2 = { it: String -> println(it.lowercase()) }
    val lower3: (String) -> Unit = { it -> println(it.lowercase()) }
    val lower4: (String) -> Unit = { println(it.lowercase()) }

    //Appel
    lower("Coucou")

    val hour: (Int) -> Int = { it / 60 }
    println(hour(123))

    val max: (Int, Int) -> Int = { a, b -> Math.max(a, b) }
    val reverse = { it: String -> it.reversed() }

    var minToMinHour: ((Int?) -> Pair<Int, Int>?)? = { if (it == null) null else Pair(it / 60, it % 60) }
    //val minToMinHour : (Int?)-> Pair<Int, Int>? = { it?.let { Pair(it/60, it%60) }}
    println(minToMinHour?.invoke(123))
    println(minToMinHour?.invoke(null))
    minToMinHour = null


}