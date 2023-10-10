package com.example.productsapo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productsapo.model.Products
import com.example.productsapo.contract.ListProductContract
import com.example.productsapo.model.MetaData
import com.example.productsapo.model.Variants
import com.example.productsapo.presenter.ListProductPresenters
import com.example.productsapo.R
import com.example.productsapo.databinding.ActivityListProductBinding
import com.example.productsapo.view.adapter.ProductAdapter
import com.example.productsapo.view.LoadMoreOnScrollListener

import kotlinx.coroutines.Job

class ListProductActivity : AppCompatActivity(), ListProductContract {

    private lateinit var binding: ActivityListProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var listProductPresenter: ListProductPresenters


    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var currentPage = 1
    private var limit = 10
    private var job: Job? = null
    private var checkCancel = true
    private var checkType: Boolean = true

    private var productAdapter: ProductAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listProductPresenter = ListProductPresenters(this)
        listProductPresenter.getListProduct(currentPage)
        linearLayoutManager = LinearLayoutManager(this)
        displayListProduct()
        initOnclick()


    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }


    override fun callListProduct(mListProduct: MutableList<Products>, metadata: MetaData) {

        binding.tvListProductQuantity.text =
            metadata.total.toString() + resources.getString(R.string.daucach) + resources.getString(
                R.string.sanpham
            )
        binding.tvListProductTitle.text = resources.getString(R.string.sanpham)
        if (currentPage == 1) {
            productAdapter.setData(mListProduct)
        } else {
            productAdapter.removeLoading()
            isLoading = false
            productAdapter.setData(mListProduct)

        }
        loadMore(metadata)

    }

    override fun callListVariant(mlistVariant: MutableList<Variants>, metadata: MetaData) {

        binding.tvListProductQuantity.text =
            metadata.total.toString() + resources.getString(R.string.daucach) + resources.getString(
                R.string.sanpham
            )
        binding.tvListProductTitle.text = resources.getString(R.string.quanlykho)
        if (currentPage == 1) {
            productAdapter.setData(mlistVariant)
        } else {
            productAdapter.removeLoading()
            isLoading = false
            productAdapter.setData(mlistVariant)

        }
        loadMore(metadata)


    }

    private fun nextPage() {
        binding.rclvListProductListproduct.addOnScrollListener(
            object : LoadMoreOnScrollListener(linearLayoutManager) {

                override fun loadMoreItems() {
                    isLoading = true
                    currentPage++
                    if (ProductAdapter.VIEW_TYPE_PRODUCT == productAdapter.modeView) {
                        listProductPresenter.getListProduct(currentPage)
                    } else if (ProductAdapter.VIEW_TYPE_VARIANT == productAdapter.modeView) {
                        listProductPresenter.getListVariant(currentPage)
                    }

                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

                override fun isLastPage(): Boolean {

                    return isLastPage
                }

            })
    }

    private fun getOnClick() {

        binding.includeDetailHeader.ivlistProductVariant.setOnClickListener {
            productAdapter.clearListProduct()
            isLastPage = false
            currentPage = 1
            checkType = !checkType
            if (checkType) {

                binding.includeDetailHeader.ivlistProductVariant.setImageResource(R.drawable.baseline_dialpad_24)
                binding.tvListProductTitle.setText(R.string.sanpham)
                productAdapter.modeView = ProductAdapter.VIEW_TYPE_PRODUCT
                listProductPresenter.getListProduct(currentPage)


            } else {

                binding.includeDetailHeader.ivlistProductVariant.setImageResource(R.drawable.baseline_apartment_24)
                binding.tvListProductTitle.setText(R.string.quanlykho)
                productAdapter.modeView = ProductAdapter.VIEW_TYPE_VARIANT
                listProductPresenter.getListVariant(currentPage)

            }
        }


    }

    private fun initOnclick() {

        getOnClick()
        detailProduct()
        detailVariant()
        nextPage()
        pullRefresh()
        search()
        back()
    }

    private fun displayListProduct() {
        linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        productAdapter = ProductAdapter()
        binding.rclvListProductListproduct.apply {
            layoutManager = linearLayoutManager
            adapter = productAdapter
            addItemDecoration(dividerItemDecoration)
        }

    }

    private fun loadMore(metaData: MetaData) {
        return (
                if (metaData.total > currentPage * limit) {
                    productAdapter.addLoading()
                } else {
                    isLastPage = true
                }
                )
    }

    private fun back() {
        binding.includeDetailHeader.ivListProductBack.setOnClickListener {
            finish()
        }
    }

    private fun pullRefresh() {

        binding.srlListProduct.setOnRefreshListener {
            currentPage = 1
            productAdapter.clearListProduct()
            if (productAdapter.getType() == ProductAdapter.VIEW_TYPE_PRODUCT) {
                listProductPresenter.getListProduct(currentPage)
            } else {
                listProductPresenter.getListVariant(currentPage)
            }
            binding.edtProductListSearch.setText("")

            binding.srlListProduct.isRefreshing = false
            isLastPage = false
        }
    }

    private fun search() {

        binding.edtProductListSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                productAdapter.clearListProduct()
                currentPage = 1

                val textSearch: String = p0.toString()
                if (productAdapter.getType() == ProductAdapter.VIEW_TYPE_PRODUCT) {
                    listProductPresenter.searchProduct(textSearch)


                } else if (productAdapter.getType() == ProductAdapter.VIEW_TYPE_VARIANT) {
                    listProductPresenter.searchVariant(textSearch)

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.edtProductListSearch.setOnClickListener { checkCancel = false }
        if (!checkCancel) {
            binding.tvlistProductCancel.visibility = View.VISIBLE
        } else {
            binding.tvlistProductCancel.visibility = View.GONE
        }

    }

    private fun detailProduct() {
        productAdapter.onClickItemProduct = { id ->
            val intent = Intent(this, DetailProductActivity::class.java)
            intent.putExtra(DetailProductActivity.ID_PRODUCT, id)
            startActivity(intent)
        }
    }

    private fun detailVariant() {
        productAdapter.onClickItemVariant = { idProduct, idVariant ->
            val intent = Intent(this, DetailVariantActivity::class.java)
            intent.putExtra(DetailProductActivity.ID_PRODUCT, idProduct)
            intent.putExtra(DetailProductActivity.ID_VARIANT, idVariant)
            startActivity(intent)
        }

    }

}

