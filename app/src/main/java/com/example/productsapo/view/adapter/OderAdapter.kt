package com.example.productsapo.view.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapo.R
import com.example.productsapo.api.format.NumberFormat.formatNumber
import com.example.productsapo.databinding.DialogDeleteProductBinding
import com.example.productsapo.databinding.DialogKeyBinding
import com.example.productsapo.model.OrderLineItem
import com.example.productsapo.view.dialog.KeyDialog


class OderAdapter(var listOrderLineItem: MutableList<OrderLineItem>, var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindingDialog: DialogKeyBinding
    private lateinit var dialog: Dialog
    var onClickItemOrder: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(itemView)
    }


    override fun getItemCount(): Int {

        return listOrderLineItem.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val orderViewHolder: OrderViewHolder = holder as OrderViewHolder

        val orDerItem = listOrderLineItem[position].variant

        orderViewHolder.tvOrderName.text = orDerItem.name
        orderViewHolder.tvOrderSKU.text = orDerItem.sku
        orderViewHolder.tvOrderPrice.text = orDerItem.variantretailprice.formatNumber()
        orderViewHolder.tvOrderQuantity.text = listOrderLineItem[position].quantity.formatNumber()

        orderViewHolder.tvOrderQuantity.setOnClickListener {
            showDialogKey(listOrderLineItem[position])
            bindingDialog.tvKeyConfirm.setOnClickListener {
                listOrderLineItem[position].quantity =
                    (bindingDialog.tvKeyQuantity.text.toString().replace(",", "")
                        .toDouble())
                orderViewHolder.tvOrderQuantity.text =
                    listOrderLineItem[position].quantity.formatNumber()



                onClickItemOrder?.invoke()
                dialog.dismiss()
                if (bindingDialog.tvKeyQuantity.text.toString().replace(",", "")
                        .toDouble().toInt() == 0
                ) {
                    showAlertDialog(listOrderLineItem[position])
                }
            }
        }

        if (orDerItem.getAvailable() < 1) {
            orderViewHolder.llOrderLackOfGoods.visibility = View.VISIBLE
        } else {
            orderViewHolder.llOrderLackOfGoods.visibility = View.GONE
        }
        orderViewHolder.ivOrderDeLeTe.setOnClickListener {
            showAlertDialog(listOrderLineItem[position])

        }
        orderViewHolder.ivOrderSubtract.setOnClickListener {
            if (listOrderLineItem[position].quantity <= 1.0) {
                showAlertDialog(listOrderLineItem[position])
            } else {
                listOrderLineItem[position].quantity--
                orderViewHolder.tvOrderQuantity.text =
                    listOrderLineItem[position].quantity.formatNumber()
            }
            onClickItemOrder?.invoke()
        }
        orderViewHolder.ivOrderAdd.setOnClickListener {

            listOrderLineItem[position].quantity++
            orderViewHolder.tvOrderQuantity.text =
                listOrderLineItem[position].quantity.formatNumber()

            onClickItemOrder?.invoke()
        }
    }

    inner class OrderViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var tvOrderName: TextView = v.findViewById(R.id.tvOrderName)
        var tvOrderSKU: TextView = v.findViewById(R.id.tvOrderSKU)
        var tvOrderPrice: TextView = v.findViewById(R.id.tvOrderPrice)
        var tvOrderQuantity: TextView = v.findViewById(R.id.tvOrderQuantity)
        var ivOrderDeLeTe: ImageView = v.findViewById(R.id.ivOrderDelete)
        var ivOrderAdd: ImageView = v.findViewById(R.id.ivOrderAdd)
        var ivOrderSubtract: ImageView = v.findViewById(R.id.ivOrderSubtract)
        var llOrderLackOfGoods: LinearLayout = v.findViewById(R.id.llOrderLackOfGoods)


    }

    private fun showAlertDialog(orderLineItem: OrderLineItem) {
        val builder = AlertDialog.Builder(context)
        val bindingDialog = DialogDeleteProductBinding.inflate(LayoutInflater.from(context))
        builder.setView(bindingDialog.root)
        val dialog = builder.create()
        bindingDialog.tvDialogDeleteProductContent.text = context.getString(
            R.string.ban_co_chac_chan_muon_xoa_san_pham_nay_ra_khoi_don_hang,
            orderLineItem.variant.name
        )
        dialog.show()

        bindingDialog.btnDialogDeleteProductCancel.setOnClickListener {
            Toast.makeText(context, R.string.xoathanhcong, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        bindingDialog.btnDialogDeleteProductConfirm.setOnClickListener {
            listOrderLineItem.remove(orderLineItem)
            onClickItemOrder?.invoke()
            //notifyItemRemoved(position)
            notifyDataSetChanged()
            dialog.dismiss()
        }

    }

    private fun showDialogKey(orderLineItem: OrderLineItem) {
        bindingDialog = DialogKeyBinding.inflate(LayoutInflater.from(context))
        val keyboardDialog = KeyDialog(context, bindingDialog)
        dialog = keyboardDialog.onCreateDialog()
        bindingDialog.tvKeyQuantity.text = orderLineItem.quantity.formatNumber()
        dialog.show()
        bindingDialog.tvKeyOut.setOnClickListener {
            dialog.dismiss()
        }
    }

}