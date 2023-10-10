package com.example.productsapo.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.productsapo.databinding.FragmentProductBinding
import com.example.productsapo.view.activity.ListProductActivity
import com.example.productsapo.view.activity.OrderActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var binding: FragmentProductBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProductListProduct.setOnClickListener {
            val intent = Intent(context, ListProductActivity::class.java)
            startActivity(intent)
        }

        binding.ctlProductAddOrder.setOnClickListener {
            val intent = Intent(context,OrderActivity :: class.java)
            startActivity(intent)
        }
    }



}