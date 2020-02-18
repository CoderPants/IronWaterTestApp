package com.ironwater.testapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ironwater.testapp.model.Product
import com.ironwater.testapp.storage.Repository
import com.ironwater.testapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream

/*
* Using it as Single Source of Truth principle
* All fragments will be hosted in the MainActivity
*/
class MainViewModel : ViewModel(){

    private val repository = Repository()

    private val observableProducts : MutableLiveData<List<Product>> = MutableLiveData(emptyList())

    private lateinit var products : List<Product>

    fun getProducts() : LiveData<List<Product>> = observableProducts

    fun getDataFromFile(fileInputStream: InputStream) =
        CoroutineScope(Dispatchers.IO).launch {

            val productsFromFile = repository.getDataFromFile(fileInputStream)
            products = productsFromFile
            withContext(Dispatchers.Main){
                observableProducts.value = productsFromFile
            }
        }

    fun findProductById(id : Long) : Product{
        for(product in products){
            if(product.isbn == id)
                return product
        }
        return Constants.EMPTY_PRODUCT
    }

}