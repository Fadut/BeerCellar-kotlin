package com.example.beer_cellar.repository

import com.example.beer_cellar.models.Beer
import retrofit2.Call
import retrofit2.http.*


interface BeerService {

    @GET("beers")
    fun getAllBeers(): Call<List<Beer>>

    @GET("beers/{user}")
    fun getBeersByUser(@Path("user") user: String): Call<List<Beer>>

    @POST("beers") // add beer
    fun addBeer(@Body beer: Beer): Call<Beer>

    @PUT("beers/{id}") // update beer
    fun updateBeer(@Path("id") id: Int, @Body beer: Beer): Call<Beer>

    @DELETE("beers/{id}")
    fun deleteBeer(@Path("id") id: Int): Call<Beer>
}