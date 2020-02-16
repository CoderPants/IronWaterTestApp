package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ironwater.testapp.R
import com.ironwater.testapp.model.Description
import com.ironwater.testapp.model.Product
import com.ironwater.testapp.ui.activities.MainActivity
import com.ironwater.testapp.ui.rvadapters.ProductsAdapter
import com.ironwater.testapp.utils.Constants
import com.ironwater.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.product_list_fragment.*

class ProductListFragment : Fragment(R.layout.product_list_fragment) {

    private lateinit var viewModel : MainViewModel
    private val rvAdapter: ProductsAdapter = ProductsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() =
        rv_for_products.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setHasFixedSize(true)
        }


    private fun subscribeObservers() =
        viewModel.getProducts().observe(viewLifecycleOwner, Observer { products ->

            if(products.isEmpty()){
                loading_indicator.visibility = View.VISIBLE
                loadDataFromFile()
            }
            else{
                rvAdapter.setProducts(products)
                loading_indicator.visibility = View.GONE
                //Log.i(Constants.LOG_TAG, "Data size in adapter: ${rvAdapter.itemCount}")
            }
        })

    private fun loadDataFromFile() {
        val inputStream = resources.openRawResource(R.raw.products)
        viewModel.getDataFromFile(inputStream)
    }
}