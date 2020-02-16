package com.ironwater.testapp.storage

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.ironwater.testapp.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader

class Repository {

    suspend fun getDataFromFile(inputStream : InputStream) : List<Product> {
        val result = ArrayList<Product>()

        withContext(CoroutineScope(Dispatchers.IO).coroutineContext){
            val jsonArray : JsonArray = JsonParser
                    .parseReader(InputStreamReader(inputStream, "UTF-8"))
                    .asJsonArray

            val gson = GsonBuilder().create()

            for (element in jsonArray)
                result.add(gson.fromJson(element, Product::class.java))
        }

        withContext(Dispatchers.IO){
            inputStream.close()
        }

        return result
    }
}