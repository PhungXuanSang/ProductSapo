package com.example.productsapo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsapo.model.Products
import com.example.productsapo.model.Variants
import com.example.productsapo.R
import com.example.productsapo.api.format.NumberFormat.formatNumber


class ProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_PRODUCT: Int = 0
        const val VIEW_TYPE_VARIANT: Int = 1
        const val VIEW_TYPE_LOADING: Int = 2
    }


    var isLoadingAdd: Boolean = false
    var onClickItemProduct: ((idProduct: Int) -> Unit)? = null
    var onClickItemVariant: ((idProduct: Int, idVariant: Int) -> Unit)? = null
    var modeView = VIEW_TYPE_PRODUCT
    private var listData : MutableList<Any> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        if ((position == listData.size - 1 && isLoadingAdd)) {
            return VIEW_TYPE_LOADING
        }
        return modeView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_product, parent, false)
        if (VIEW_TYPE_LOADING == viewType) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_actionbar, parent, false)
            return LoadingViewHolder(itemView)
        } else {
            if (modeView == VIEW_TYPE_PRODUCT) {
                return ProductViewHolder(view)
            } else if (modeView == VIEW_TYPE_VARIANT) {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_variant, parent, false)
                return VariantViewHolder(itemView)
            }
        }

        return ProductViewHolder(view)

    }



    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == VIEW_TYPE_PRODUCT) {
            val productViewHolder: ProductViewHolder = holder as ProductViewHolder
            var image: String? = null
            val productItem = listData[position] as Products

            if (productItem.images.isNotEmpty()) {
                image = productItem.images[0].fullpath
            }
            Glide.with(productViewHolder.ivListProductImage.context).load(image)
                .error(R.drawable.image).placeholder(R.drawable.image)
                .into(productViewHolder.ivListProductImage)
            productViewHolder.tvListProductName.text = productItem.name//
            productViewHolder.tvListProductVersion.text =
                productItem.variants.size.toString()

            productViewHolder.tvListProductOnHand.text =
                productItem.getTotalOnhand().formatNumber()
            productViewHolder.itemListProduct.setOnClickListener {
                onClickItemProduct?.invoke(productItem.id!!)
            }

        } else if (holder.itemViewType == VIEW_TYPE_VARIANT) {
            val variantViewHolder: VariantViewHolder = holder as VariantViewHolder
            var image: String? = null
            val variantItem = listData[position] as Variants
            if (variantItem.images.isNotEmpty()) {
                image = variantItem.images[0].fullpath
            }

            Glide.with(variantViewHolder.ivListVariantImage.context).load(image)
                .error(R.drawable.image).placeholder(R.drawable.image)
                .into(variantViewHolder.ivListVariantImage)
            variantViewHolder.tvListVariantName.text = variantItem.name
            variantViewHolder.tvListVariantSKU.text = variantItem.sku

            variantViewHolder.tvListVariantAvailable.text =
                variantItem.getOnhand().formatNumber()
            variantViewHolder.tvListVariantPrice.text =variantItem.variantretailprice.formatNumber()
//                variantItem.getAvailable().formatNumber()

            variantViewHolder.itemListVariant.setOnClickListener {
                onClickItemVariant?.invoke(
                    variantItem.productId!!,
                    variantItem.id!!
                )
            }

        }


    }
    fun getType(): Int {
        return modeView
    }

    fun setData( data: List<Any>) {
        val positionStart=listData.size
        listData.addAll(data)
        notifyItemRangeInserted(positionStart, data.size)

    }

    fun addLoading() {
        isLoadingAdd = true
        listData.add(Variants())
        notifyItemInserted(listData.lastIndex)
    }

    fun removeLoading() {
        isLoadingAdd = false
        val position =listData.size-1
        listData.removeAt(position)
        notifyItemRemoved(position)
    }


    fun clearListProduct() {
        val itemCount = listData.size
        listData.clear()
        notifyItemRangeRemoved(0, itemCount)
    }


    inner class ProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var ivListProductImage: ImageView = v.findViewById(R.id.ivListProductImage)
        var tvListProductName: TextView = v.findViewById(R.id.tvListProductName)
        var tvListProductVersion: TextView = v.findViewById(R.id.tvListProductVersion)
        var tvListProductOnHand: TextView = v.findViewById(R.id.tvListProductCotheban)
        var itemListProduct: LinearLayout = v.findViewById(R.id.itemlistproduct)
    }

    inner class VariantViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var ivListVariantImage: ImageView = v.findViewById(R.id.ivListVariantImage)
        var tvListVariantName: TextView = v.findViewById(R.id.tvListVariantName)
        var tvListVariantSKU: TextView = v.findViewById(R.id.tvListVariantSKU)
        var tvListVariantPrice: TextView = v.findViewById(R.id.tvListVariantPrice)
        var tvListVariantAvailable: TextView = v.findViewById(R.id.tvListVariantTonkho)
        var itemListVariant: LinearLayout = v.findViewById(R.id.itemlistvariant)
    }

    inner class LoadingViewHolder(v: View) : RecyclerView.ViewHolder(v) {}

}