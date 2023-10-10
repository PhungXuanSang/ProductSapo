package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelProductAPI.ImageAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.ProductsAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.VariantsAPI
import java.io.Serializable


class Products() : Serializable
{
    var id : Int? = null//
    var  tenant_id : Int? = null
    var created_on : String? = null
    var modified_on : String? = null
    var status : String? = null
    var category : String?=null
    var description : String? = null
    var image_path : String? = null
    var image_name : String? = null
    var name : String? = null//
    var variants : MutableList<Variants> = mutableListOf()//
    var options : MutableList<Options>? = null
    var images : MutableList<Image> = mutableListOf()
    var product_medicines : String? = null
    var brand : String?=null

    constructor(product : ProductsAPI) : this(){
        id = product.id
        name = product.name
        variants.addAll(convertVariants(product.variants))
        images.addAll(convertImages(product.images))
        brand = product.brand
        category = product.category
        description = product.description
        status = product.status

    }


    fun listProduct(list: List<ProductsAPI>) : List<Products> {
        val product = Products()
        return list.map {product }
    }

    private fun convertImages(imageList: List<ImageAPI>?): List<Image>{
        if(imageList == null) return listOf()
        val tmpList = mutableListOf<Image>()
        imageList.forEach {
            tmpList.add(Image(it))
        }
        return tmpList
    }
    private fun convertVariants(variants: List<VariantsAPI>?): List<Variants>{
        if(variants == null) return listOf()
        val tmpList = mutableListOf<Variants>()
        variants.forEach {
            tmpList.add(Variants(it))
        }
        return tmpList
    }

    fun getTotalOnhand(): Double {
        var count : Double =0.0
        count+=variants.sumOf { it -> it.inventories.sumOf { it.onhand ?: 0.0 } }
        return count

    }
}

