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

    private var hasDialogShown : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(activity as MainActivity).get(MainViewModel::class.java)

        val activity = (activity as AppCompatActivity)
        viewModel.setupToolBar( activity, activity.findViewById(R.id.main_toolbar), true )

        val productID : Long = arguments!!.getLong(Constants.PRODUCT_ID)
        fillFragment(productID)

        hasDialogShown = savedInstanceState?.getBoolean(Constants.HAS_DIALOG) ?: false

        if(hasDialogShown)
            dialog.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Constants.HAS_DIALOG, hasDialogShown)
    }

    override fun onStop() {
        super.onStop()

        if(dialog.isShowing)
            dialog.dismiss()
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

        companyUrl = description.url
        tv_for_company_link.text = companyUrl

        createDialog()
        tv_for_company_link.setOnClickListener{
            hasDialogShown = true
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
                hasDialogShown = false
            }
            .setNegativeButton(R.string.dialog_negative){
            _,_->
                dialog.dismiss()
                hasDialogShown = false
            }

        dialog = dialogBuilder.create()
    }
}