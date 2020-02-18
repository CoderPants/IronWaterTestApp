package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ironwater.testapp.R
import com.ironwater.testapp.model.Product
import com.ironwater.testapp.ui.activities.MainActivity
import com.ironwater.testapp.utils.Constants
import com.ironwater.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.product_description_fragment.*
import kotlinx.android.synthetic.main.product_rv_element.*

class ProductDescriptionFragment : Fragment(R.layout.product_description_fragment) {

    private lateinit var viewModel: MainViewModel

    private lateinit var product : Product

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpToolBar( (activity as AppCompatActivity).findViewById(R.id.main_toolbar) )

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        val id : Long = arguments!!.getLong("product")
        Log.i(Constants.LOG_TAG, "Products in DESCRIPTION FRAGMENT : $id")
        fillFragment(id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity!!.menuInflater.inflate(R.menu.descriprion_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.action_about_company -> {
                val bundle = bundleOf("product" to product.isbn)
                navController
                    .navigate(R.id.action_productDescriptionFragment_to_productCompanyFragment, bundle)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setUpToolBar(toolbar: Toolbar) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar( toolbar )
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun fillFragment(id : Long) {
        product = viewModel.findProductById(id)

        val image = activity!!
            .resources
            .getIdentifier(product.image, "drawable", activity!!.packageName)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            iv_for_product_image.setImageDrawable(activity!!.resources.getDrawable(image, null))
        else
            iv_for_product_image.setImageDrawable(activity!!.resources.getDrawable(image))

        description_fragment_tv_for_product_id.text = product.isbn.toString()
        description_fragment_tv_for_product_name.text = product.title
        description_fragment_tv_for_product_description.text = product.description.about

    }
}