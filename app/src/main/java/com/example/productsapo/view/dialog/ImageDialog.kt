package com.example.productsapo.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.productsapo.R

class ImageDialog(context: Context) : Dialog(context) {

    private var imagePath: String = ""

    // Set image path
    fun setImage(path: String) {
        imagePath = path
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_image)

        // Load and display image using Glide
        val imageView = findViewById<ImageView>(R.id.ivDialogImage)

        Glide.with(context)
            .load(imagePath)
            .apply(RequestOptions().centerCrop())
            .into(imageView)

        // Set up dialog UI and behavior
        // ...
    }
}