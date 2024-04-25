package com.example.beer_cellar.models

import java.io.Serializable

data class Beer (val id: Int, val user: String, val brewery: String, val name: String, val style: String,
                 val abv: Double, val volume: Double, val pictureUrl: String, val howMany: Int )
    : Serializable {

    override fun toString(): String {
        return "$id: $brewery, $name, $abv%"
    }
}