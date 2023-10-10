package com.example.productsapo.api.modelAPI.modelProductAPI

import com.google.gson.annotations.SerializedName

data class ProductsAPI (
    @SerializedName("id")
    var id : Int,
    @SerializedName("tenant_id")
    var tenantid: Int,
    @SerializedName("created_on")
    var createdon: String,
    @SerializedName("modified_on")
    var modifiedon: String,
    @SerializedName("status")
    var status : String,
    @SerializedName("brand")
    var brand : String,
    @SerializedName("description")
    var description : String,
    @SerializedName("image_path")
    var imagepath: String,
    @SerializedName("image_name")
    var imagename: String,
    @SerializedName("name")
    var name : String,
    @SerializedName("opt1")
    var opt1 : String,
    @SerializedName("opt2")
    var opt2 : String,
    @SerializedName("opt3")
    var opt3 : String,
    @SerializedName("category")
    var category : String,
    @SerializedName("category_code")
    var categorycode: String,
    @SerializedName("tags")
    var tags : String,
    @SerializedName("medicine")
    var medicine : String,
    @SerializedName("product_type")
    var producttype: String,
    @SerializedName("variants")
    var variants : MutableList<VariantsAPI>,
    @SerializedName("options")
    var options : MutableList<OptionsAPI>,
    @SerializedName("images")
    var images : MutableList<ImageAPI>?,
    @SerializedName("product_medicines")
    var productmedicines: String,

    )