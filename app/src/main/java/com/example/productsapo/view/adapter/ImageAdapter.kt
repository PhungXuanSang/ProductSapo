package com.example.productsapo.view.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productsapo.model.Image
import com.example.productsapo.R
import com.example.productsapo.api.format.NumberFormat.formatNumber
import com.example.productsapo.databinding.DialogImageBinding
import com.example.productsapo.view.dialog.ImageDialog

class ImageAdapter(var list: MutableList<Image>, var context: Context) :
    RecyclerView.Adapter<ImageAdapter.LoadingViewHolder>() {
    private lateinit var bindingDialog: DialogImageBinding
    private lateinit var dialog: Dialog

    //   var onClickItemImage: ((idImage: Int) -> Unit)? = null
    var onClickItemImage: (() -> Unit)? = null

    inner class LoadingViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var ivImage: ImageView = v.findViewById(R.id.ivImageProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return LoadingViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {

        Glide.with(holder.ivImage.context).load(list[position].fullpath).into(holder.ivImage)
        holder.ivImage.setOnClickListener {

                val dialog = ImageDialog(holder.itemView.context)
            list[position].fullpath?.let { it1 -> dialog.setImage(it1) } // Truyền đường dẫn ảnh cho dialog
                dialog.show()

            showDialogImage(list[position])

            onClickItemImage?.invoke()
        }

    }

    private fun showDialogImage(image: Image) {


//        val dialogBuilder = AlertDialog.Builder(context)
//        val inflater = LayoutInflater.from(context)
//        val dialogView: View = inflater.inflate(R.layout.dialog_image, null)
//
//
//
//        dialogBuilder.setView(dialogView)
//        val alertDialog: AlertDialog = dialogBuilder.create()
//        alertDialog.show()

    }


}