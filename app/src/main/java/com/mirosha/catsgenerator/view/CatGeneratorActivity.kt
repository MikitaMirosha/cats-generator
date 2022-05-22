package com.mirosha.catsgenerator.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mirosha.catsgenerator.databinding.ActivityCatGeneratorBinding
import com.mirosha.catsgenerator.model.CatResponse
import com.mirosha.catsgenerator.utils.NetworkStatus
import com.mirosha.catsgenerator.utils.UrlConstants.BASE_URL
import com.mirosha.catsgenerator.viewmodel.CatGeneratorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatGeneratorActivity : AppCompatActivity() {

    private val viewModel by viewModels<CatGeneratorViewModel>()
    private lateinit var binding: ActivityCatGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCatData()
        binding.btnGiveMeCat.setOnClickListener { fetchCatResponse() }
    }

    private fun fetchCatData() {
        fetchCatResponse()

        viewModel.response.observe(this) { response ->
            when (response) {
                is NetworkStatus.Loading -> {
                    binding.pbLoadingImage.visibility = View.VISIBLE

                    Log.i(CatGeneratorActivity::class.java.simpleName, LOADING_STATUS)
                }

                is NetworkStatus.Success -> {
                    loadCatImage(binding, response)
                    binding.pbLoadingImage.visibility = View.GONE

                    Log.i(CatGeneratorActivity::class.java.simpleName, SUCCESS_STATUS)
                }

                is NetworkStatus.Error -> {
                    binding.pbLoadingImage.visibility = View.GONE
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.i(CatGeneratorActivity::class.java.simpleName, ERROR_STATUS)
                }
            }
        }
    }

    private fun fetchCatResponse() {
        viewModel.getRandomCat()
        binding.pbLoadingImage.visibility = View.VISIBLE
    }

    private fun loadCatImage(
        binding: ActivityCatGeneratorBinding,
        response: NetworkStatus<CatResponse>
    ) {
        Glide.with(binding.root.context)
            .load(BASE_URL + response.data?.url)
            .centerCrop()
            .into(binding.ivCatImage)
    }

    private companion object {
        const val LOADING_STATUS = "Network Status: LOADING"
        const val SUCCESS_STATUS = "Network Status: SUCCESS"
        const val ERROR_STATUS = "Network Status: ERROR"
    }
}