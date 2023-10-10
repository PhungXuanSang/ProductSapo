package com.example.productsapo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productsapo.contract.DetailVariantContract
import com.example.productsapo.model.Products
import com.example.productsapo.model.Variants
import com.example.productsapo.presenter.DetailVariantPresenter
import com.example.productsapo.R
import com.example.productsapo.view.adapter.DetailVariantAdapter
import com.example.productsapo.view.adapter.ImageAdapter
import com.example.productsapo.databinding.ActivityDetailProductBinding

// compannion object
// lateintit ko co privete
// lateintit co pri
// var
// val
// prive + ...
// override của hệ thống
// override inlemin
// hàm fun
// prive fun
// class

class DetailVariantActivity : AppCompatActivity(), DetailVariantContract {

    companion object {
        const val ID_PRODUCT = "ID_PRODUCT"
        const val ID_VARIANT = "ID_VARIANT"
    }

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var listVariant: MutableList<Variants>
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var detailVariantAdapter: DetailVariantAdapter
    private var productId: Int = 0
    private var variantId: Int = 0
    private var product: Products = Products()
    private var variant: Variants = Variants()
    private var detailVariantPresenter: DetailVariantPresenter = DetailVariantPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listVariant = mutableListOf()
        productId = intent.getIntExtra(ID_PRODUCT, 0)
        variantId = intent.getIntExtra(ID_VARIANT, 0)
        detailVariantPresenter.detailVariant(productId, variantId)
        back()

    }

    override fun callDetailVariant(variants: Variants) {
        variant = variants
        if (!variant.packsize) {
            detailVariantPresenter.detailProduct(productId)
        }
        showData()

    }

    override fun callDetailProduct(products: Products) {
        product = products
        showData()
    }

    private fun showData() {

        getListDetailVariant()

        if (listVariant.isEmpty()) {
            binding.crdDetailProductNameProduct.visibility = View.GONE
            binding.llDetailProductAddInfo.visibility = View.GONE
            binding.crdDetailStatus.visibility = View.GONE
            binding.crdDetailProductVariantProduct.visibility = View.GONE
            binding.rlDetailProductAddInfo.visibility = View.GONE
            showListImage()
            showInfo()
            showPrice()
            wareHouse()
            showAddInfo()

        } else {
            binding.crdDetailProductNameProduct.visibility = View.GONE
            binding.llDetailProductAddInfo.visibility = View.GONE
            binding.crdDetailProductInfo.visibility = View.VISIBLE
            binding.crdDetailStatus.visibility = View.GONE
            binding.crdDetailProductVariantProduct.visibility = View.VISIBLE
            binding.rlDetailProductAddInfo.visibility = View.GONE
            showListImage()
            showInfo()
            showPrice()
            wareHouse()
            showAddInfo()
            showListVariantProduct()
        }
    }

    private fun back() {
        binding.ivDetailProdctBack.setOnClickListener {
            finish()

        }
    }

    private fun getListDetailVariant(): MutableList<Variants> {
        for (i in 0 until product.variants.size) {
            if (variant.id == product.variants[i].packsizeRootId) {
                listVariant.add(product.variants[i])
            }
        }
        return listVariant
    }

    private fun showListImage() {
        if (product.images.isEmpty()) {
            binding.rclvImage.visibility = View.VISIBLE
        } else {
            onAdapterImage()
        }
    }

    private fun onAdapterImage() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rclvImage.layoutManager = linearLayoutManager
        imageAdapter = ImageAdapter(product.images, this)
        binding.rclvImage.adapter = imageAdapter

    }

    private fun showInfo() {
        binding.tvDetailProductName.text = variant.name
        binding.tvDetailProductSKU.text = variant.sku
        binding.tvDetailProductBarcode.text = variant.barcode
        if (variant.unit.isNullOrEmpty()) {
            binding.tvDetailProductDonViTinh.text = "---"
        } else {
            binding.tvDetailProductDonViTinh.text = variant.unit
        }
        val weightText = (variant.weightValue)?.toString() + variant.weightUnit
        binding.tvDetailProductKhoiLuong.text = weightText
    }


    private fun wareHouse() {
        binding.tvDetailProductTonkho.text = variant.getOnhand().toString()
        binding.tvDetailProductCotheban.text = variant.getAvailable().toString()
    }

    private fun showPrice() {
        if (variant.taxIncluded) {
            binding.tvDetailProductTaxIncluded.text = getString(R.string.dabaogomthue)
        } else {
            binding.tvDetailProductTaxIncluded.text = getString(R.string.chuabaogomthue)
        }
        if (variant.taxable) {
            binding.lltvDetailTaxable.visibility = View.VISIBLE

            binding.tvDetailInputTax.text =
                variant.inputVatRate?.toString() + resources.getString(R.string.phantram)
            binding.tvDetailOutputTax.text =
                variant.outputVatRate?.toString() + resources.getString(R.string.phantram)
        } else binding.lltvDetailTaxable.visibility = View.GONE
        binding.tvDetailProductImportPrice.text = variant.variantimportprice.toString()
        binding.tvDetailProductRetailPrice.text = variant.variantretailprice.toString()
        binding.tvDetailProductWholesalePrice.text = variant.variantwholeprice.toString()
    }

    private fun showAddInfo() {

        if (variant.sellable) {
            binding.ivDetailSellable.setImageResource(R.drawable.baseline_check_24)
            val color = ContextCompat.getColor(this, R.color.green)
            binding.ivDetailSellable.setBackgroundColor(color)
            binding.tvDetailSellable.text = getString(R.string.chophepban)
        } else {
            binding.ivDetailSellable.setImageResource(R.drawable.baseline_close_24)
            binding.tvDetailSellable.text = getString(R.string.khongchophepban)
            val color = ContextCompat.getColor(this, R.color.red)

            binding.ivDetailSellable.setBackgroundColor(color)
        }
    }

    private fun showListVariantProduct() {

        val linearLayoutManager = LinearLayoutManager(this)
        binding.rclvDetailProductVariantOrProduct.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvDetailProductVariantOrProduct.addItemDecoration(dividerItemDecoration)
        detailVariantAdapter = DetailVariantAdapter(listVariant)
        binding.rclvDetailProductVariantOrProduct.adapter = detailVariantAdapter
    }


}