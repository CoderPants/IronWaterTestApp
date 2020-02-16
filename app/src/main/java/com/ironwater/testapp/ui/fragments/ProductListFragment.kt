package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ironwater.testapp.R
import com.ironwater.testapp.ui.activities.MainActivity
import com.ironwater.testapp.utils.Constants
import com.ironwater.testapp.viewmodel.MainViewModel

class ProductListFragment : Fragment() {

    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        subscribeObservers()
    }

    private fun subscribeObservers() =
        viewModel.getProducts().observe(viewLifecycleOwner, Observer { products ->

            if(products.isEmpty()){
                //loadDataFromFile()
                Log.i(Constants.LOG_TAG, "Loading data IN FRAGMENT")
            }
            else{
                Log.i(Constants.LOG_TAG, "Data from file IN FRAGMENT is: $products")
            }
        })

    /*private fun loadDataFromFile() {
        val inputStream = resources.openRawResource(R.raw.products)
        viewModel.getDataFromFile(inputStream)
    }*/

}