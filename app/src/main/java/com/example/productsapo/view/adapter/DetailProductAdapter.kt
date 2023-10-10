package com.example.productsapo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsapo.model.Variants
import com.example.productsapo.R
import com.example.productsapo.api.format.NumberFormat.formatNumber


class DetailProductAdapter(private var listVariant: MutableList<Variants>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onClickItemVariant: ((idProduct: Int, idVariant: Int) -> Unit)? = null

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
            listVariant[position].name?.replace(listVariant[position].productName + " - ", "")
        detailProductViewHolder.tvListDetailProductSKU.text = listVariant[position].sku
        detailProductViewHolder.tvListDetailProductPrice.text =
            listVariant[position].variantwholeprice.formatNumber()
        detailProductViewHolder.tvListDetailProductOnHand.text =
            listVariant[position].getOnhand().formatNumber()
        detailProductViewHolder.tvListDetailProductAvailable.text =
            listVariant[position].getAvailable().formatNumber()
        detailProductViewHolder.onClickItemVariant.setOnClickListener {
            onClickItemVariant?.invoke(
                listVariant[position].productId!!,
                listVariant[position].id!!
            )
        }

        if (listVariant[position].packsize) {
            detailProductViewHolder.ivListDetailProductVariant.visibility = View.VISIBLE
        } else {
            detailProductViewHolder.ivListDetailProductVariant.visibility = View.GONE
        }

    }

    inner class DetailProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var ivListDetailProductVariant: ImageView = v.findViewById(R.id.ivListDetailProductVariant)
        var ivListDetailProductImage: ImageView = v.findViewById(R.id.ivListDetailProductImage)
        var tvListDetailProductVariant: TextView = v.findViewById(R.id.tvListDetailProductVariant)
        var tvListDetailProductSKU: TextView = v.findViewById(R.id.tvListDetailProductSKU)
        var tvListDetailProductPrice: TextView = v.findViewById(R.id.tvListDetailProductPrice)
        var tvListDetailProductOnHand: TextView = v.findViewById(R.id.tvListDetailProductTonkho)
        var tvListDetailProductAvailable: TextView =
            v.findViewById(R.id.tvListDetailProductCotheban)
        var onClickItemVariant: LinearLayout = v.findViewById(R.id.llDetailProductVariant)
    }


}