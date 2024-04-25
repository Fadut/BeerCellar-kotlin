package com.example.beer_cellar.models

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beer_cellar.repository.BeerRepository

class BeerViewModel : ViewModel() {
    private val repository = BeerRepository()
    val beersLiveData: LiveData<List<Beer>> = repository.beersLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: MutableLiveData<String>  = MutableLiveData()
    val reloadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        // reload()
    }

    fun reload() {
        repository.getBeers()
    }

    fun getBeersByUser(user : String) {
        repository.getBeersByUser(user)
    }

    operator fun get(index: Int): Beer? {
        return beersLiveData.value?.get(index)
    }

    fun add(beer: Beer) {
        repository.add(beer)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(beer: Beer) {
        repository.update(beer)
    }

    fun sortByName() {
        repository.sortByName()
    }

    fun sortByBrewery() {
        repository.sortByBrewery()
    }

    fun sortByAbv() {
        repository.sortByAbv()
    }

    fun filterByName(name: String) {
        repository.filterByName(name)
    }

    fun filterByAbv(abv : Double) {
        repository.filterByAbv(abv)
    }
}