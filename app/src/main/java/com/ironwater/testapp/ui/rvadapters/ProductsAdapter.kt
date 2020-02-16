package com.ironwater.testapp.ui.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironwater.testapp.R
import com.ironwater.testapp.model.Product
import kotlinx.android.synthetic.main.product_rv_element.view.*

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>(){

    private val products : MutableList<Product> = ArrayList()

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

    class ProductViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(product : Product){
            itemView.tv_for_product_id.text = product.id.toString()
            itemView.tv_for_product_name.text = product.title
        }
    }
}