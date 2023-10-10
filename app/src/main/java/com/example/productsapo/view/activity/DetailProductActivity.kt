package com.example.productsapo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productsapo.contract.DetailProductContract
import com.example.productsapo.model.Products
import com.example.productsapo.presenter.DetailProductPresenter
import com.example.productsapo.R
import com.example.productsapo.databinding.ActivityDetailProductBinding
import com.example.productsapo.view.adapter.DetailProductAdapter
import com.example.productsapo.view.adapter.ImageAdapter


class DetailProductActivity : AppCompatActivity(), DetailProductContract {

    companion object {
        const val ID_PRODUCT = "ID_PRODUCT"
        const val ID_VARIANT = "ID_VARIANT"
    }

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var mDetailProductPresenter: DetailProductPresenter
    private lateinit var product: Products
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var detailProductAdapter: DetailProductAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val productId = intent.getIntExtra(ID_PRODUCT, 0)
        mDetailProductPresenter = DetailProductPresenter(this)
        mDetailProductPresenter.detailProduct(productId)
        back()


    }

    override fun callDetailProduct(products: Products) {
        product = products

        if (product.variants[0].name.equals(product.name) && product.variants.size == 1) {
            showListImage()
            showInfo()
            showPrice()
            wareHouse()
            moreInfo()
            showAddInfo()
            binding.crdDetailProductNameProduct.visibility = View.GONE
            binding.crdDetailProductVariantProduct.visibility = View.GONE
        } else {
            binding.crdDetailProductNameProduct.visibility = View.VISIBLE
            binding.crdDetailProductInfo.visibility = View.GONE
            binding.crdDetailProductPrice.visibility = View.GONE
            binding.tvDetailProductNameProduct.text = product.name
            binding.crvDetailProductWareHouse.visibility = View.GONE
            showListImage()
            moreInfo()
            onAdapterListVariant()
            showAddInfo()
        }
    }

    private fun showAddInfo() {
        if (product.variants[0].description.isNullOrEmpty()) {
            binding.tvDetailProductDescription.text = "-----"
        } else {
            binding.tvDetailProductDescription.text = product.variants[0].description!!
        }
        if (product.brand.isNullOrEmpty()) {
            binding.tvDetailProductBrand.text = "-----"
        } else {
            binding.tvDetailProductBrand.text = product.brand!!
        }
        if (product.category.isNullOrEmpty()) {
            binding.tvDetailProductProductCategory.text = "-----"
        } else {
            binding.tvDetailProductProductCategory.text = product.category!!
        }

        if (product.variants[0].sellable) {
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

    private fun showInfo() {
        binding.tvDetailProductName.text = product.name
        binding.tvDetailProductSKU.text = product.variants[0].sku
        binding.tvDetailProductBarcode.text = product.variants[0].barcode
        if (product.variants[0].unit.isNullOrEmpty()) {
            binding.tvDetailProductDonViTinh.text = "---"
        } else {
            binding.tvDetailProductDonViTinh.text = product.variants[0].unit
        }
        val weightText =
            (product.variants[0].weightValue)?.toString() + product.variants[0].weightUnit
        binding.tvDetailProductKhoiLuong.text = weightText
    }

    private fun showPrice() {
        if (product.variants[0].taxIncluded) {
            binding.tvDetailProductTaxIncluded.text = getString(R.string.dabaogomthue)
        } else {
            binding.tvDetailProductTaxIncluded.text = getString(R.string.chuabaogomthue)
        }
        if (product.variants[0].taxable) {
            binding.lltvDetailTaxable.visibility = View.VISIBLE

            binding.tvDetailInputTax.text =
                product.variants[0].inputVatRate?.toString() + resources.getString(R.string.phantram)
            binding.tvDetailOutputTax.text =
                product.variants[0].outputVatRate?.toString() + resources.getString(R.string.phantram)
        } else {
            binding.lltvDetailTaxable.visibility = View.GONE
        }
        binding.tvDetailProductImportPrice.text = product.variants[0].variantimportprice.toString()
        binding.tvDetailProductRetailPrice.text = product.variants[0].variantretailprice.toString()
        binding.tvDetailProductWholesalePrice.text =
            product.variants[0].variantwholeprice.toString()
    }

    private fun wareHouse() {
        binding.tvDetailProductTonkho.text = product.variants[0].getOnhand().toString()
        binding.tvDetailProductCotheban.text = product.variants[0].getAvailable().toString()
    }

    private fun moreInfo() {
        binding.rlDetailProductAddInfo.setOnClickListener {
            if (binding.llDetailProductAddInfo.visibility == View.VISIBLE) {
                binding.llDetailProductAddInfo.visibility = View.GONE
                binding.ivDetailProductDownOrUp.setImageResource(R.drawable.ic_down)
            } else {
                binding.llDetailProductAddInfo.visibility = View.VISIBLE
                binding.ivDetailProductDownOrUp.setImageResource(R.drawable.baseline_arrow_forward_ios_24)
            }
        }
    }

    private fun onAdapterListVariant() {

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.rclvDetailProductVariantOrProduct.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvDetailProductVariantOrProduct.addItemDecoration(dividerItemDecoration)

        detailProductAdapter = DetailProductAdapter(product.variants)
        binding.rclvDetailProductVariantOrProduct.adapter = detailProductAdapter

        DetailProductToVariant()

    }

    private fun onAdapterImage() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rclvImage.layoutManager = linearLayoutManager
        imageAdapter = ImageAdapter(product.images, this)
        binding.rclvImage.adapter = imageAdapter
        Log.d("TAG", "onAdapterImage: " + binding.rclvImage.size)
    }

    private fun showListImage() {
        if (product.images.isEmpty()) {
            binding.rclvImage.visibility = View.VISIBLE
        } else {
            onAdapterImage()
        }

    }

    private fun back() {
        binding.ivDetailProdctBack.setOnClickListener {
            finish()
        }
    }

    private fun DetailProductToVariant() {
        detailProductAdapter.onClickItemVariant = { idProduct, idVariant ->
            val intent = Intent(this, DetailVariantActivity::class.java)
            intent.putExtra(DetailVariantActivity.ID_PRODUCT, idProduct)
            intent.putExtra(DetailVariantActivity.ID_VARIANT, idVariant)
            startActivity(intent)
        }

    }


}