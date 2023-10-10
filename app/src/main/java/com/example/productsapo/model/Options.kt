package com.example.productsapo.model

import java.io.Serializable

class Options() : Serializable
{
    var id : Int? = null
    var name : String? = null
    var position : Int? = null
    var values : MutableList<String>? = null
}


