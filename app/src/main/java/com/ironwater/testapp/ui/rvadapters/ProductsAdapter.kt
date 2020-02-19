package com.ironwater.testapp.ui.rvadapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironwater.testapp.R
import com.ironwater.testapp.model.Product
import kotlinx.android.synthetic.main.product_rv_element.view.*

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>(){

    private val products : MutableList<Product> = ArrayList()
    private lateinit var callback : RVCallBack

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_rv_element, parent, false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind( products[position] )

    fun setProducts(new : List<Product>) {
        products.clear()
        products.addAll(new)
        notifyDataSetChanged()
    }

    fun setCallBack(callback: RVCallBack){
        this.callback = callback
    }

    inner class ProductViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(product : Product){
            itemView.tv_for_product_id.text = product.isbn.toString()
            itemView.tv_for_product_name.text = product.title
            itemView.iv_for_product_image.setImageDrawable( callback.getDrawable(product.image) )

            itemView.setOnClickListener{
                callback.redirectToDescriptionFragment(productId = product.isbn)
            }
        }
    }

    interface RVCallBack{
        fun redirectToDescriptionFragment(productId : Long)

        fun getDrawable(imageName : String) : Drawable
    }
}