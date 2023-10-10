package com.example.productsapo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsapo.R
import com.example.productsapo.api.format.NumberFormat.formatNumber
import com.example.productsapo.model.OrderLineItem


class SelectVariantAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        const val TYPE_VARIANT : Int = 1
        const val TYPE_LOADING : Int = 2
    }


    var onClickItemVariant: ((orderLineItem: OrderLineItem) -> Unit)? = null
    private var isLoadingAdd : Boolean = false
    private var  listOrderLineItem:  MutableList<OrderLineItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(TYPE_VARIANT == viewType){
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_variant, parent, false)
            VariantViewHolder(itemView)
        }else{
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_actionbar, parent, false)
            LoadingViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
      return listOrderLineItem.size
    }
    override fun getItemViewType(position: Int): Int {
        if(position == listOrderLineItem.size-1 && isLoadingAdd){
            return TYPE_LOADING
        }
        return TYPE_VARIANT
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.itemViewType == TYPE_VARIANT){
            val variantViewHolder: VariantViewHolder = holder as VariantViewHolder
            var image: String? = null
            val orderItem = listOrderLineItem[position]
            if (orderItem.variant.images.isNotEmpty()) {
                image = orderItem.variant.images[0].fullpath
            }

            Glide.with(variantViewHolder.ivListVariantImage.context).load(image)
                .error(R.drawable.image).placeholder(R.drawable.image)
                .into(variantViewHolder.ivListVariantImage)
            variantViewHolder.tvListVariantName.text =orderItem.variant.name
            variantViewHolder.tvListVariantSKU.text = orderItem.variant.sku

            variantViewHolder.tvListVariantOnHand.text =
                orderItem.variant.getAvailable().formatNumber()

            variantViewHolder.tvListVariantPrice.text =
                orderItem.variant.variantretailprice.formatNumber()

            variantViewHolder.itemListVariant.setOnClickListener {
                variantViewHolder.tvVariantTotal.visibility=View.VISIBLE
                orderItem.quantity= orderItem.quantity + 1
                variantViewHolder.tvVariantTotal.text = (orderItem.quantity).formatNumber()
                onClickItemVariant?.invoke(orderItem)

            }

        }

    }
    fun addFooterLoading(){
        isLoadingAdd = true
        listOrderLineItem.add(OrderLineItem())
    }
    fun removeFooterLoading(){
        isLoadingAdd = false
        val position: Int = listOrderLineItem.size-1
        listOrderLineItem.removeAt(position)
        notifyItemRemoved(position)
    }
    fun addList(list: MutableList<OrderLineItem>){
        val start=listOrderLineItem.size
        listOrderLineItem.addAll(list)
        notifyItemRangeInserted(start, list.size)
    }
    inner class VariantViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var ivListVariantImage: ImageView = v.findViewById(R.id.ivListVariantImage)
        var tvListVariantName: TextView = v.findViewById(R.id.tvListVariantName)
        var tvListVariantSKU: TextView = v.findViewById(R.id.tvListVariantSKU)
        var tvListVariantPrice: TextView = v.findViewById(R.id.tvListVariantPrice)
        var tvListVariantOnHand: TextView = v.findViewById(R.id.tvListVariantTonkho)
        var itemListVariant: LinearLayout = v.findViewById(R.id.itemlistvariant)
        var  tvVariantTotal : TextView = v.findViewById(R.id.tvVariantTotal)
    }

    inner class LoadingViewHolder(v: View) : RecyclerView.ViewHolder(v) {}

}