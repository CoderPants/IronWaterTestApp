package com.ironwater.testapp.viewmodel

import android.util.Log
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

class MainViewModel : ViewModel(){

    private val repository = Repository()

    private val products : MutableLiveData<List<Product>> = MutableLiveData(emptyList())

    fun getProducts() : LiveData<List<Product>> = products

    fun getDataFromFile(fileInputStream: InputStream) =
        CoroutineScope(Dispatchers.IO).launch {
            val productsFromFile = repository.getDataFromFile(fileInputStream)
            Log.i(Constants.LOG_TAG, "Data from file $productsFromFile")
            withContext(Dispatchers.Main){
                products.value = productsFromFile
            }
        }

}