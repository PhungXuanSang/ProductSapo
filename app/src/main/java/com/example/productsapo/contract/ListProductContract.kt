package com.example.productsapo.contract

import com.example.productsapo.model.MetaData
import com.example.productsapo.model.Products
import com.example.productsapo.model.Variants

interface ListProductContract {

    fun callListProduct(mListProduct: MutableList<Products>, metadata: MetaData)

    fun callListVariant(mlistVariant: MutableList<Variants>, metadata: MetaData)

}
