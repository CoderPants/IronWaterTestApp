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

class ProductCompanyFragment : Fragment(R.layout.product_company_fragment) {

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)
        subscribeObservers()
    }

    private fun subscribeObservers() =
        viewModel.getProducts().observe(viewLifecycleOwner, Observer { products ->

            Log.i(Constants.LOG_TAG, "Products in COMPANY FRAGMENT : $products")
        })
}