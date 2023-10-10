package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelProductAPI.ImageAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.InventoriesAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.VariantPricesAPI
import com.example.productsapo.api.modelAPI.modelProductAPI.VariantsAPI
import java.io.Serializable


class Variants() : Serializable {

    var id : Int?=null
    var createdon: String?=null
    var modifiedon: String?=null
    var description : String?=null
    var name : String?=null
    var opt1 : String?=null
    var status : String?=null
    var sku : String?=null
     var barcode : String?=null
     var weightUnit : String?=null
     var weightValue : Double?=null
     var variantretailprice: Double =0.0
     var variantwholeprice: Double = 0.0
     var variantimportprice: Double =0.0
     var unit : String?=null
     var taxIncluded:Boolean=false
     var opt2 : String?=null
     var opt3 : String?=null
     var sellable : Boolean = false
     var inputVatRate : Double?=null
     var outputVatRate : Double?=null
     var productName : String?=null
     var packsize : Boolean =false
     var productId : Int?=null
     var packsizeRootId : Int?=null
     var taxable : Boolean= true
     var packSizeQuantity : Double?=null
     var total :Double?=null
    //
    var quantity : Double? = null
     var variantprices: MutableList<VariantPrice> = mutableListOf()
     var inventories : MutableList<Inventories> = mutableListOf()
     var images : MutableList<Image> = mutableListOf()


      constructor(variant: VariantsAPI) : this() {
         id = variant.id
         name = variant.name
          taxable = variant.taxable
          description = variant.description
          opt1 = variant.opt1
          opt2 = variant.opt2
          opt3 = variant.opt3
          sellable = variant.sellable
          sku = variant.sku
          barcode = variant.barcode
          weightValue = variant.weightvalue
          weightUnit = variant.weightunit
          unit = variant.unit
          inputVatRate = variant.inputvatrate
          outputVatRate = variant.outputvatrate
          productName = variant.productname
          packsize = variant.packsize
          productId = variant.productid
          packsizeRootId = variant.packsizerootid
          packSizeQuantity = variant.packsizequantity
          taxIncluded = variant.taxincluded
          variantretailprice = variant.variantretailprice
          variantimportprice = variant.variantimportprice
          variantwholeprice = variant.variantwholeprice
          //
          quantity = variant.quantity
          images.addAll(convertImages(variant.images))
          variantprices.addAll(convertVariantPrices(variant.variantprices))
          inventories.addAll(convertInventories(variant.inventories))
      }

     private fun convertInventories(inventories: List<InventoriesAPI>?): List<Inventories>{
         if(inventories == null) return listOf()
         val tmpList = mutableListOf<Inventories>()
         inventories.forEach {
             tmpList.add(Inventories(it))
         }
         return tmpList
     }

     private fun convertVariantPrices(variantPrices: List<VariantPricesAPI>?): List<VariantPrice>{
         if(variantPrices == null) return listOf()
         val tmpList = mutableListOf<VariantPrice>()
         variantPrices.forEach {
             tmpList.add(VariantPrice(it))
         }
         return tmpList
     }

     private fun convertImages(imageList: List<ImageAPI>?): List<Image>{
         if(imageList == null) return listOf()
         val tmpList = mutableListOf<Image>()
         imageList.forEach {
             tmpList.add(Image(it))
         }
         return tmpList
     }

     fun isOtp():Boolean{
         return opt1=="Mặc định"
     }
     fun getOnhand(): Double {
         return inventories.sumOf { it.onhand ?: 0.0 }
     }

     fun getAvailable() : Double{
         return inventories.sumOf { it.available?: 0.0 }
     }
     fun getRetailPrice():Double {
         var retailPrice = 0.0
         for (item in variantprices) {
//             if (item.price_list?.isretailPrice()!!) {
                 retailPrice=item.value!!
//             }
         }
         return retailPrice
     }
     fun getImportPrice():Double {
         var importPrice = 0.0
         for (item in variantprices) {
//             if (item.price_list?.isImportPrice()!!) {
                 importPrice=item.value!!
//             }
         }
         return importPrice
     }
     fun getWholesalePrice():Double {
         var wholesalePrice = 0.0
         for (item in variantprices) {
//             if (item.price_list?.isWholesalePrice()!!) {
                 wholesalePrice=item.value!!
//             }
         }
         return wholesalePrice
     }

 }

