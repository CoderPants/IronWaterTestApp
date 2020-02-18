package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ironwater.testapp.R
import com.ironwater.testapp.ui.activities.MainActivity
import com.ironwater.testapp.utils.Constants
import com.ironwater.testapp.viewmodel.MainViewModel

class ProductDescriptionFragment : Fragment(R.layout.product_description_fragment) {

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id : Int? = arguments?.getInt("product")
        Log.i(Constants.LOG_TAG, "Products in DESCRIPTION FRAGMENT : $id")

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        //subscribeObservers()
    }

   /* private fun subscribeObservers() =
        viewModel.getProducts().observe(viewLifecycleOwner, Observer { products ->

            //Log.i(Constants.LOG_TAG, "Products in DESCRIPTION FRAGMENT : $products")
        })*/
}