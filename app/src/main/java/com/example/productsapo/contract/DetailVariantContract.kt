package com.example.productsapo.contract

import com.example.productsapo.model.Products
import com.example.productsapo.model.Variants

interface DetailVariantContract {
    fun callDetailVariant(variants: Variants)
    fun callDetailProduct(products: Products)
}