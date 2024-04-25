package com.example.beer_cellar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beer_cellar.models.Beer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BeerRepository {
    private val baseUrl = "https://anbo-restbeer.azurewebsites.net/api/"

    private val beerService: BeerService
    val beersLiveData: MutableLiveData<List<Beer>> = MutableLiveData<List<Beer>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val reloadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        beerService = build.create(BeerService::class.java)
        // getBeers()
    }

    fun getBeers() {
        reloadingLiveData.value = true
        beerService.getAllBeers().enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                reloadingLiveData.value = false
                if (response.isSuccessful) {
                    val b: List<Beer>? = response.body()
                    beersLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")

                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                reloadingLiveData.value = false
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun getBeersByUser(user : String) {
        reloadingLiveData.value = true
        beerService.getBeersByUser(user).enqueue(object : Callback<List<Beer>> {
            override fun onResponse(call: Call<List<Beer>>, response: Response<List<Beer>>) {
                reloadingLiveData.value = false
                if (response.isSuccessful) {
                    val b: List<Beer>? = response.body()
                    beersLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")

                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Beer>>, t: Throwable) {
                reloadingLiveData.value = false
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun add(beer: Beer) {
        beerService.addBeer(beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                 if (response.isSuccessful) {
                     Log.d("APPLE", "Added: " + response.body())
                     updateMessageLiveData.postValue("Added: " + response.body())
                     getBeers()

                 } else {
                     val message = response.code().toString() + " " + response.message()
                     errorMessageLiveData.postValue(message)
                     Log.d("APPLE", message)
                 }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun update(beer: Beer) {
        beerService.updateBeer(beer.id, beer).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Updated: " + response.body())

                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun delete(id : Int) {
        beerService.deleteBeer(id).enqueue(object : Callback<Beer> {
            override fun onResponse(call: Call<Beer>, response: Response<Beer>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated" + response.body())
                    updateMessageLiveData.postValue("Deleted" + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Beer>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun sortByName() {
        beersLiveData.value = beersLiveData.value?.sortedBy { it.name }
    }

    fun sortByBrewery() {
        beersLiveData.value = beersLiveData.value?.sortedBy { it.brewery }
    }

    fun sortByAbv() {
        beersLiveData.value = beersLiveData.value?.sortedBy { it.abv }
    }

    // filters
    fun filterByName(name: String) {
        if (name.isBlank()) {
            getBeersByUser("123@213.com")
        } else {
            beersLiveData.value = beersLiveData.value?.filter { beer -> beer.name.contains(name) }
        }
    }

    fun filterByAbv(abv: Double) {
        if (abv == 0.0) {
            getBeersByUser("123@213.com")
        } else {
            beersLiveData.value = beersLiveData.value?.filter { beer -> beer.abv == abv }
        }
    }

}