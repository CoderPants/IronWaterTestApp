package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ironwater.testapp.R
import com.ironwater.testapp.ui.activities.MainActivity
import com.ironwater.testapp.ui.rvadapters.ProductsAdapter
import com.ironwater.testapp.utils.Constants
import com.ironwater.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.product_list_fragment.*


class ProductListFragment : Fragment(R.layout.product_list_fragment) {

    private lateinit var viewModel : MainViewModel

    private val rvAdapter: ProductsAdapter = ProductsAdapter()
    private lateinit var recyclerView : RecyclerView

    private lateinit var navController : NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        val activity = (activity as AppCompatActivity)
        viewModel.setupToolBar(
            activity,
            activity.findViewById(R.id.main_toolbar),
            false,
            R.string.books
        )

        initRecyclerView()
        subscribeObservers()
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if(savedInstanceState != null){
            val savedRecyclerLayoutState: Parcelable? = savedInstanceState.
                getParcelable(Constants.SCROLL_RV_POSITION)
            recyclerView.layoutManager?.onRestoreInstanceState(savedRecyclerLayoutState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        //'Cos we have single activity and each rotation of the child fragment
        // will trigger all back stack fragment's onSaveInstanceState
        if(this::recyclerView.isInitialized)
            outState.putParcelable(
                Constants.SCROLL_RV_POSITION,
                recyclerView.layoutManager?.onSaveInstanceState()
            )
}

    private fun initRecyclerView() {
        rvAdapter.setCallBack( createRVAdapterCallBack() )
        recyclerView = rv_for_products

        recyclerView.apply {
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
            }
        })

    private fun loadDataFromFile() {
        val inputStream = resources.openRawResource(R.raw.products)
        viewModel.getDataFromFile(inputStream)
    }

    private fun createRVAdapterCallBack() : ProductsAdapter.RVCallBack =
        object : ProductsAdapter.RVCallBack {
            override fun redirectToDescriptionFragment(productId: Long) {
                val bundle = bundleOf(Constants.PRODUCT_ID to productId)
                navController
                    .navigate(R.id.action_productListFragment_to_productDescriptionFragment, bundle)
            }
        }


}