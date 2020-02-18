package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
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

    private lateinit var navController : NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {

        rvAdapter.setCallBack(object : ProductsAdapter.RVCallBack{
            override fun redirectToDescriptionFragment(productId: Int) {
                val bundle = bundleOf("product" to productId)
                navController
                    .navigate(R.id.action_productListFragment_to_productDescriptionFragment, bundle)
            }
        })

        rv_for_products.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            setHasFixedSize(true)
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
        R.id.go_back_to_main -> {
            activity!!.onBackPressed()
            true
        }
        R.id.action_about_company -> {
            Log.i(Constants.LOG_TAG, "About company")
            false
        }
        else -> super.onOptionsItemSelected(item)
    }

}