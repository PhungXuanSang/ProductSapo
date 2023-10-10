package com.example.productsapo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsapo.model.Variants
import com.example.productsapo.R
import com.example.productsapo.api.format.NumberFormat.formatNumber

class DetailVariantAdapter(var listVariant: MutableList<Variants>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_detail_product, parent, false)
        return DetailProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listVariant.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val detailProductViewHolder: DetailProductViewHolder = holder as DetailProductViewHolder
        var image: String? = null
        if (listVariant[position].images.isNotEmpty()) {
            image = listVariant[position].images[0].fullpath
        }
        Glide.with(detailProductViewHolder.ivListDetailProductImage.context).load(image)
            .error(R.drawable.image).placeholder(R.drawable.image)
            .into(detailProductViewHolder.ivListDetailProductImage)
        detailProductViewHolder.tvListDetailProductVariant.text =
            listVariant[position].name?.replace(
                listVariant[position].productName + " - ",
                ""
            )
        detailProductViewHolder.tvListDetailProductSKU.text = listVariant[position].sku
        detailProductViewHolder.tvListDetailProductPrice.text =
            listVariant[position].variantretailprice.formatNumber()
        detailProductViewHolder.tvListDetailProductOnHand.text =
            listVariant[position].getOnhand().formatNumber()
        detailProductViewHolder.tvListDetailProductAvailable.text =
            listVariant[position].getAvailable().formatNumber()

    }

    inner class DetailProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var ivListDetailProductImage: ImageView = v.findViewById(R.id.ivListDetailProductImage)
        var tvListDetailProductVariant: TextView = v.findViewById(R.id.tvListDetailProductVariant)
        var tvListDetailProductSKU: TextView = v.findViewById(R.id.tvListDetailProductSKU)
        var tvListDetailProductPrice: TextView = v.findViewById(R.id.tvListDetailProductPrice)
        var tvListDetailProductOnHand: TextView = v.findViewById(R.id.tvListDetailProductTonkho)
        var tvListDetailProductAvailable: TextView =
            v.findViewById(R.id.tvListDetailProductCotheban)
    }
}