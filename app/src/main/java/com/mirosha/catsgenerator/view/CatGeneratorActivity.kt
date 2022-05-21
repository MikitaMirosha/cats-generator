package com.mirosha.catsgenerator.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.RoundedCornersTransformation
import com.mirosha.catsgenerator.databinding.ActivityTestCatGeneratorBinding
import com.mirosha.catsgenerator.utils.NetworkStatus
import com.mirosha.catsgenerator.viewmodel.CatGeneratorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatGeneratorActivity : AppCompatActivity() {

    private val viewModel by viewModels<CatGeneratorViewModel>()
    private lateinit var binding: ActivityTestCatGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestCatGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchCatData()
        binding.iViewRefresh.setOnClickListener { fetchCatResponse() }
    }

    private fun fetchCatData() {
        fetchCatResponse()

        viewModel.response.observe(this) { response ->
            when (response) {
                is NetworkStatus.Loading -> {
                    binding.pBarCat.visibility = View.VISIBLE
                }

                is NetworkStatus.Success -> {
                    response.data?.let {
                        binding.iViewCat.load(response.data.url) {
                            transformations(RoundedCornersTransformation(ROUNDED_CORNER_RADIUS))
                        }
                    }
                    binding.pBarCat.visibility = View.GONE
                }

                is NetworkStatus.Error -> {
                    binding.pBarCat.visibility = View.GONE
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun fetchCatResponse() {
        viewModel.getRandomCat()
        binding.pBarCat.visibility = View.VISIBLE
    }

    private companion object {
        const val ROUNDED_CORNER_RADIUS = 16f
    }
}