package com.ironwater.testapp.viewmodel

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ironwater.testapp.R
import com.ironwater.testapp.model.Product
import com.ironwater.testapp.storage.Repository
import com.ironwater.testapp.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
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

    //To retrieve data from file
    private val observableProducts : MutableLiveData<List<Product>> = MutableLiveData(emptyList())

    //To use this as in all child fragments
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

        //Wll never be occurred
        return Product()
    }

    fun getDrawableByName(imageName : String, activity: AppCompatActivity) : Drawable{
        val image = activity
            .resources
            .getIdentifier(imageName, "drawable", activity.packageName)

        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            activity.resources.getDrawable(image, null)
        else
            activity.resources.getDrawable(image)
    }

    fun setupToolBar(activity: AppCompatActivity, toolbar: Toolbar,
                     enableUpButton : Boolean, titleId : Int )=
        activity.apply {
            setSupportActionBar(toolbar)
            toolbar.findViewById<TextView>(R.id.toolbar_tv_books).setText(titleId)

            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(enableUpButton)

            if(enableUpButton)
                toolbar.setNavigationOnClickListener { onBackPressed() }
        }

}