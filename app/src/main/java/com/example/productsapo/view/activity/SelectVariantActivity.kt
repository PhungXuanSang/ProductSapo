package com.example.productsapo.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productsapo.contract.SelectVariantContract
import com.example.productsapo.databinding.ActivitySelectVariantBinding
import com.example.productsapo.model.MetaData
import com.example.productsapo.model.OrderLineItem
import com.example.productsapo.presenter.SelectVariantPresenter
import com.example.productsapo.view.LoadMoreOnScrollListener
import com.example.productsapo.view.adapter.SelectVariantAdapter


class SelectVariantActivity : AppCompatActivity(), SelectVariantContract {


    companion object {
        const val KEY_LINE_ITEM = "KEY_DATA_LINE_ITEM"
        const val SWITCH_STATE = "KEY_SWITCH_STATE"
        const val MY_PREFS = "MY_PREFS"
    }

    private val sharedPreferences by lazy {
        getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
    }

    private lateinit var binding: ActivitySelectVariantBinding
    private lateinit var selectVariantPresenter: SelectVariantPresenter
    private lateinit var adapter: SelectVariantAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    private var total: Int = 0
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var currentPage = 1
    private var limit = 10
    private var mOrderLineItem: MutableList<OrderLineItem> = mutableListOf()
    private var lineItems: MutableList<OrderLineItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectVariantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectVariantPresenter = SelectVariantPresenter(this)
        linearLayoutManager = LinearLayoutManager(this)
        selectVariantPresenter.getListVariant(currentPage)
        //       lineItems = intent.getSerializableExtra(KEY_LINE_ITEM) as? ArrayList<OrderLineItem> //

        binding.rclvSelectVariant.layoutManager = linearLayoutManager
        adapter = SelectVariantAdapter()
        binding.rclvSelectVariant.adapter = adapter

        search()
        onClick()
    }


    override fun callListVariant(
        listOrderLineItem: MutableList<OrderLineItem>,
        metaData: MetaData
    ) {

        if (!lineItems.isNullOrEmpty()) {
            lineItems?.let { updateQuantity(it, listOrderLineItem) }
        }
        if (mOrderLineItem.isNotEmpty()) {
            updateQuantity(mOrderLineItem, listOrderLineItem)
        }
        if (currentPage == 1) {
            adapter.addList(listOrderLineItem)

        } else {
            adapter.removeFooterLoading()
            isLoading = false
            adapter.addList(listOrderLineItem)
        }
        loadMore(metaData)

    }

    private fun loadMore(metaData: MetaData) {
        return (
                if (metaData.total > currentPage * limit) {
                    adapter.addFooterLoading()
                } else {
                    isLastPage = true
                }
                )
    }

    private fun updateQuantity(
        sourceList: MutableList<OrderLineItem>,
        targetList: MutableList<OrderLineItem>
    ) {
        for (item in sourceList) {
            val matchingItem = targetList.find { item.variant.id == it.variant.id }
            matchingItem?.quantity = item.quantity
        }
    }

    private fun onClick() {
        binding.ivSelectVariantBack.setOnClickListener { finish() }
        binding.btnSelectVariantReselect.setOnClickListener { resetQuantity() }
        changeSwitchCompat()
        nextPage()
        goToOrder()
        pullRefresh()

    }

    private fun resetQuantity() {
        for (item in mOrderLineItem) {
            item.quantity = 0.0
        }
        updateQuantity(lineItems!!, mOrderLineItem)
        adapter.notifyDataSetChanged()
    }

    private fun changeSwitchCompat() {

        val savedSwitchState = sharedPreferences.getBoolean(SWITCH_STATE, false)
        binding.scSelectVariantChange.isChecked = savedSwitchState

        binding.llSelectVariantBottom.visibility = if (savedSwitchState) {
            View.VISIBLE
        } else {
            View.GONE
        }

        binding.scSelectVariantChange.setOnCheckedChangeListener { _, isChecked ->
            binding.llSelectVariantBottom.visibility = if (isChecked) {
                View.VISIBLE
            } else {
                View.GONE
            }
            sharedPreferences.edit().putBoolean(SWITCH_STATE, isChecked).apply()
        }
    }

    private fun nextPage() {
        binding.rclvSelectVariant.addOnScrollListener(
            object : LoadMoreOnScrollListener(linearLayoutManager) {

                override fun loadMoreItems() {
                    isLoading = true
                    currentPage++
                    selectVariantPresenter.getListVariant(currentPage)

                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun isLastPage(): Boolean {

                    return isLastPage
                }

            })
    }

    private fun search() {

        binding.edtSelectVariantSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val tv = binding.edtSelectVariantSearch.text.toString()

                selectVariantPresenter.searchVariant(tv)

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun pullRefresh() {

        binding.srlSelectVariant.setOnRefreshListener {

            binding.srlSelectVariant.isRefreshing = false
        }
    }

    private fun goToOrder() {
        adapter.onClickItemVariant = { orderLineItem ->
            mOrderLineItem.find { it.variant.id == orderLineItem.variant.id }?.let {
                mOrderLineItem.remove(it)
            }

            if (binding.scSelectVariantChange.isChecked) {
                if (!mOrderLineItem.contains(orderLineItem)) {
                    mOrderLineItem.add(orderLineItem)

                }
            } else {
                mOrderLineItem.add(orderLineItem)
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra(KEY_LINE_ITEM, ArrayList(mOrderLineItem))
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }

        binding.btnSelectVariantFinished.setOnClickListener {

            mOrderLineItem.removeAll { it.quantity == 0.0 }
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra(KEY_LINE_ITEM, ArrayList(mOrderLineItem))
            setResult(Activity.RESULT_OK, intent)
            finish()

        }

    }

}