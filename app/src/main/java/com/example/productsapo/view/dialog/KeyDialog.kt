package com.example.productsapo.view.dialog

import android.app.Dialog
import android.content.Context
import com.example.productsapo.R
import com.example.productsapo.api.format.NumberFormat.formatNumber
import com.example.productsapo.databinding.DialogKeyBinding
import com.example.productsapo.model.OrderLineItem


class KeyDialog(private val context: Context, private val mBinding: DialogKeyBinding) {


    fun onCreateDialog(): Dialog {
        val builder = Dialog(context)
        builder.setContentView(mBinding.root)
        onClick()
        return builder
    }

    private fun onClick() {
        mBinding.tvKeyNumber0.setOnClickListener { addNumber(0) }
        mBinding.tvNumber1.setOnClickListener { addNumber(1) }
        mBinding.tvNumber2.setOnClickListener { addNumber(2) }
        mBinding.tvNumber3.setOnClickListener { addNumber(3) }
        mBinding.tvNumber4.setOnClickListener { addNumber(4) }
        mBinding.tvNumber5.setOnClickListener { addNumber(5) }
        mBinding.tvNumber6.setOnClickListener { addNumber(6) }
        mBinding.tvNumber7.setOnClickListener { addNumber(7) }
        mBinding.tvNumber8.setOnClickListener { addNumber(8) }
        mBinding.tvNumber9.setOnClickListener { addNumber(9) }
        mBinding.ivKeyClear.setOnClickListener { mBinding.tvKeyQuantity.text = "0" }
        mBinding.ivKeyBackpace.setOnClickListener { removeOne() }
        mBinding.tvKeyDot.setOnClickListener { setDot() }
    }

    private fun limitStringToCharacters(input: String): String {
        return if (input.length > 11) {
            input.substring(0, 11)
        } else {
            input
        }
    }

    private fun addNumber(number: Int) {
        val quantity = limitStringToCharacters(mBinding.tvKeyQuantity.text.toString() + number)
        if (quantity.contains(".")) {
            val beforeQuantity = quantity.substringBeforeLast(".")
            var afterQuantity = quantity.substringAfterLast(".")
            afterQuantity = if (afterQuantity.length > 3) {
                afterQuantity.substring(0, 3)
            } else {
                afterQuantity
            }
            mBinding.tvKeyQuantity.text = context.getString(
                R.string.quantity_decimal,
                beforeQuantity.replace(",", "").toDouble().formatNumber(),
                afterQuantity
            )
        } else {
            mBinding.tvKeyQuantity.text = quantity.replace(",", "").toDouble().formatNumber()
        }
        if (mBinding.tvKeyQuantity.text.toString().replace(",", "").toDouble() > 999999.999) {
            mBinding.tvKeyQuantity.text = context.getString(R.string.max_quantity)
        }

    }

    private fun removeOne() {
        if (mBinding.tvKeyQuantity.text.isNotEmpty() && mBinding.tvKeyQuantity.text.length > 1) {
            mBinding.tvKeyQuantity.text = mBinding.tvKeyQuantity.text.dropLast(1)
        } else if (mBinding.tvKeyQuantity.text.length == 1) {
            mBinding.tvKeyQuantity.text = "0"
        }
    }

    private fun setDot() {
        if (!mBinding.tvKeyQuantity.text.contains(".")) {
            mBinding.tvKeyQuantity.text = context.getString(
                R.string.quantity,
                mBinding.tvKeyQuantity.text,
                mBinding.tvKeyDot.text
            )
        }
    }


}