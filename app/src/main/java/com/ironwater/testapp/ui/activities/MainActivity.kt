package com.ironwater.testapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ironwater.testapp.R
import com.ironwater.testapp.ui.fragments.ProductListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redirectToFragment()
    }
    private fun redirectToFragment() =
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, ProductListFragment())
            .commit()

}
