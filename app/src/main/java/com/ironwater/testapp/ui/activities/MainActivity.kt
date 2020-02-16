package com.ironwater.testapp.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ironwater.testapp.R
import com.ironwater.testapp.ui.fragments.ProductListFragment
import com.ironwater.testapp.utils.Constants
import com.ironwater.testapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        redirectToFragment()

        Log.i(Constants.LOG_TAG, "In oncreate $viewModel")
        observeProducts()

    }

    private fun redirectToFragment() =
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, ProductListFragment())
            .commit()

    private fun observeProducts() =
        viewModel.getProducts().observe(this, Observer { products ->

            if(products.isEmpty()){
                loadDataFromFile()
                Log.i(Constants.LOG_TAG, "Loading data")
            }
            else{
                Log.i(Constants.LOG_TAG, "Data from file IN ACTIVITY is: $products")
                redirectToFragment()
            }
        })



    private fun loadDataFromFile() {
        val inputStream = resources.openRawResource(R.raw.products)
        viewModel.getDataFromFile(inputStream)
    }
}
