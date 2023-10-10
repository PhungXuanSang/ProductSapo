package com.example.productsapo.model

import com.example.productsapo.api.modelAPI.modelProductAPI.ImageAPI
import java.io.Serializable

class Image() : Serializable {
    var id: Int? = null
    var fullpath: String? = null

    constructor(image: ImageAPI) : this() {
        fullpath = image.fullpath
        id = image.id
    }


}



