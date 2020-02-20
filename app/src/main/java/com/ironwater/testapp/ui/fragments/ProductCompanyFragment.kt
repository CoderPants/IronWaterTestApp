package com.ironwater.testapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ironwater.testapp.R
import com.ironwater.testapp.model.Description
import com.ironwater.testapp.ui.activities.MainActivity
import com.ironwater.testapp.utils.Constants
import com.ironwater.testapp.utils.FragmentHelper
import com.ironwater.testapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.product_company_fragment.*

class ProductCompanyFragment : Fragment(R.layout.product_company_fragment) {

    private lateinit var viewModel: MainViewModel

    private lateinit var companyUrl : String

    private lateinit var dialog: AlertDialog

    private var isDialogShowing : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        val activity = (activity as AppCompatActivity)
        viewModel.setupToolBar(
            activity,
            activity.findViewById(R.id.main_toolbar),
            true,
            R.string.about_company
        )

        val productID : Long = arguments!!.getLong(Constants.PRODUCT_ID)
        fillFragment(productID)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if(savedInstanceState != null){
            isDialogShowing = savedInstanceState.getBoolean(Constants.HAS_DIALOG)

            if(isDialogShowing)
                dialog.show()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Constants.HAS_DIALOG, isDialogShowing)
    }

    override fun onStop() {
        super.onStop()

        if(dialog.isShowing)
            dialog.dismiss()
    }

    private fun fillFragment(id: Long) {
        val description : Description = viewModel.findProductById(id).description

        val companyLogo = viewModel
            .getDrawableByName(description.companyLogo, activity = activity as AppCompatActivity)

        iv_for_company_logo.setImageDrawable(companyLogo)
        tv_for_company_name.text = description.companyName

        companyUrl = description.url
        tv_for_company_link.text = companyUrl

        createDialog()
        tv_for_company_link.setOnClickListener{
            isDialogShowing = true
            dialog.show()
        }
    }

    private fun createDialog(){
        val dialogBuilder = AlertDialog.Builder(context!!)

        dialogBuilder
            .setMessage(R.string.dialog_message)
            .setTitle(R.string.dialog_title)

        dialogBuilder
            .setPositiveButton(R.string.dialog_positive) {
                _, _ ->
                FragmentHelper.openLink(context!!, companyUrl)
                isDialogShowing = false
            }
            .setNegativeButton(R.string.dialog_negative){
            _,_->
                dialog.dismiss()
                isDialogShowing = false
            }

        dialog = dialogBuilder.create()
    }
}