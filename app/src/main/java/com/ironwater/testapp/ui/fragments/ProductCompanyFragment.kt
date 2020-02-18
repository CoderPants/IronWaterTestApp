package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ironwater.testapp.R
import com.ironwater.testapp.model.Description
import com.ironwater.testapp.ui.activities.MainActivity
import com.ironwater.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.product_company_fragment.*

class ProductCompanyFragment : Fragment(R.layout.product_company_fragment) {

    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.main_toolbar)
        setUpToolBar(toolbar)

        val id : Long = arguments!!.getLong("product")
        fillFragment(id)
    }

    private fun setUpToolBar(toolbar: Toolbar) {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun fillFragment(id: Long) {
        val description : Description = viewModel.findProductById(id).description

        val companyLogo = activity!!
            .resources
            .getIdentifier(description.companyLogo, "drawable", activity!!.packageName)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            iv_for_company_logo
                .setImageDrawable(activity!!.resources.getDrawable(companyLogo, null))
        else
            iv_for_company_logo
                .setImageDrawable(activity!!.resources.getDrawable(companyLogo))

        tv_for_company_name.text = description.companyName
        tv_for_company_link.text = description.url
    }
}