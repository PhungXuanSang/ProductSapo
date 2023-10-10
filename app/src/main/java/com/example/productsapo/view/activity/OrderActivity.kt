package com.example.productsapo.view.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productsapo.R

import com.example.productsapo.api.format.NumberFormat.formatNumber

import com.example.productsapo.databinding.ActivityOrderBinding
import com.example.productsapo.model.Order
import com.example.productsapo.model.OrderLineItem

import com.example.productsapo.view.adapter.OderAdapter


class OrderActivity : AppCompatActivity() {


    companion object {
        const val MY_PREFS: String = "MY_PREFS"
        const val ORDER_SOURCE_ID: String = "ORDER_SOURCE_ID"
    }

    private val sharedPreferences by lazy {
        getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
    }

    private lateinit var binding: ActivityOrderBinding
    private var orderSourceIdToSave: Int? = null
    private var order: Order = Order()


    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val mListOrderLineItem =
                    intent?.getSerializableExtra(SelectVariantActivity.KEY_LINE_ITEM) as ArrayList<OrderLineItem>
                if (order.orderLineItems.isEmpty()) {
                    order.orderLineItems = mListOrderLineItem
                } else {
                    val idsToRemove = mListOrderLineItem.map { it.variant.id }
                    order.orderLineItems.removeAll { idsToRemove.contains(it.variant.id) }
                    order.orderLineItems.addAll(0, mListOrderLineItem)

                }

                disPlayListOrder()
                showInfoOrder()

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        orderSourceIdToSave = sharedPreferences.getInt(ORDER_SOURCE_ID, -1)
        setOnClick()

    }


    private fun disPlayListOrder() {
        binding.llOrderAddOrder.visibility = View.GONE
        val linearLayoutManager = LinearLayoutManager(this)
        binding.rclvOrder.layoutManager = linearLayoutManager
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rclvOrder.addItemDecoration(dividerItemDecoration)
        val adapter = OderAdapter(order.orderLineItems, this)
        binding.rclvOrder.adapter = adapter
        adapter.onClickItemOrder = {
            showInfoOrder()
        }

    }

    private fun showInfoOrder() {

        binding.tvCreateOrderTax.text = order.getTotalTaxOrder().formatNumber()
        binding.tvCreateOrderTotal.text = order.getTotalQuantity().formatNumber()
        binding.tvCreateOrderTotalAmount.text = order.getTotalAmountOrder().formatNumber()
        binding.tvCreateOrderTotalMoney.text = order.getTotalMoney().formatNumber()
    }


    private fun postData() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(resources.getString(R.string.thongbao))
        alertDialogBuilder.setMessage(resources.getString(R.string.taodonhangthanhcong))

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        val handler = android.os.Handler()
        val delayMillis = 2000L
        handler.postDelayed({
            alertDialog.dismiss()
            finish()
        }, delayMillis)

    }


    private fun setOnClick() {
        binding.btnCreateOrder.setOnClickListener { postData() }
        binding.ivOrderBack.setOnClickListener { finish() }
        binding.tvOrderAddOrder.setOnClickListener {
            val intent = Intent(this, SelectVariantActivity::class.java)
            startActivity(intent)
            intent.putExtra(SelectVariantActivity.KEY_LINE_ITEM, ArrayList(order.orderLineItems))
            startForResult.launch(intent)
        }
        binding.llOrderAddOrder.setOnClickListener {
            val intent = Intent(this, SelectVariantActivity::class.java)
            startActivity(intent)
            startForResult.launch(intent)
        }
    }
}