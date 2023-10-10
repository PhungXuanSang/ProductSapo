package com.example.productsapo.api.modelAPI.modelProductAPI

import com.google.gson.annotations.SerializedName

data class VariantsAPI(

    @SerializedName("id")
    var id: Int,
    @SerializedName("tenant_id")
    var tenantid: Int,
    @SerializedName("location_id")
    var locationid: Int,
    @SerializedName("created_on")
    var createdon: String,
    @SerializedName("modified_on")
    var modifiedon: String,
    @SerializedName("category_id")
    var categoryid: Int,
    @SerializedName("brand_id")
    var brandid: Int,
    @SerializedName("product_id")
    var productid: Int,
    @SerializedName("composite")
    var composite: Boolean,
    @SerializedName("init_price")
    var initprice: Double,
    @SerializedName("init_stock")
    var initstock: Double,
    @SerializedName("variant_retail_price")
    var variantretailprice: Double,
    @SerializedName("variant_whole_price")
    var variantwholeprice: Double,
    @SerializedName("variant_import_price")
    var variantimportprice: Double,
    @SerializedName("image_id")
    var imageid: Int,
    @SerializedName("description")
    var description: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("opt1")
    var opt1: String,
    @SerializedName("opt2")
    var opt2: String,
    @SerializedName("opt3")
    var opt3: String,
    @SerializedName("product_name")
    var productname: String,
    @SerializedName("product_status")
    var productstatus: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("sellable")
    var sellable: Boolean,
    @SerializedName("sku")
    var sku: String,
    @SerializedName("barcode")
    var barcode: String,
    @SerializedName("taxable")
    var taxable: Boolean,
    @SerializedName("weight_value")
    var weightvalue: Double,
    @SerializedName("weight_unit")
    var weightunit: String,
    @SerializedName("unit")
    var unit: String,
    @SerializedName("packsize")
    var packsize: Boolean,
    @SerializedName("packsize_quantity")
    var packsizequantity: Double,
    @SerializedName("packsize_root_id")
    var packsizerootid: Int,
    @SerializedName("packsize_root_sku")
    var packsizerootsku: String,
    @SerializedName("packsize_root_name")
    var packsizerootname: String,
    @SerializedName("tax_included")
    var taxincluded: Boolean,
    @SerializedName("input_vat_id")
    var inputvatid: Int,
    @SerializedName("output_vat_id")
    var outputvatid: Int,
    @SerializedName("input_vat_rate")
    var inputvatrate: Double,
    @SerializedName("output_vat_rate")
    var outputvatrate: Double,
    @SerializedName("product_type")
    var producttype: String,
    @SerializedName("variant_prices")
    var variantprices: MutableList<VariantPricesAPI>,
    @SerializedName("inventories")
    var inventories: MutableList<InventoriesAPI>,
    @SerializedName("images")
    var images: MutableList<ImageAPI>,
    @SerializedName("warranty")
    var warranty: Boolean,
    @SerializedName("warranty_term_id")
    var warrantytermid: String,
    @SerializedName("expiration_alert_time")
    var expirationalerttime: String,

    @SerializedName("quantity")
    var quantity:Double

    )