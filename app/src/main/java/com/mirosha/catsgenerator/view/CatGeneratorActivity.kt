package com.mirosha.catsgenerator.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mirosha.catsgenerator.R
import com.mirosha.catsgenerator.databinding.ActivityCatGeneratorBinding
import com.mirosha.catsgenerator.model.CatResponse
import com.mirosha.catsgenerator.utils.Constants.BASE_URL
import com.mirosha.catsgenerator.utils.NetworkStatus
import com.mirosha.catsgenerator.utils.clearText
import com.mirosha.catsgenerator.utils.hideView
import com.mirosha.catsgenerator.utils.showView
import com.mirosha.catsgenerator.viewmodel.CatGeneratorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatGeneratorActivity : AppCompatActivity() {

    private val viewModel by viewModels<CatGeneratorViewModel>()
    private lateinit var binding: ActivityCatGeneratorBinding

    private var tagItem: String? = DEFAULT_TAG_ITEM_VALUE
    private var textItem: String? = DEFAULT_TEXT_ITEM_VALUE

    private val optionItems: HashMap<String?, String?> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchAllTags()
        fetchRandomCat()
        fetchCatData()

        showOnProgressViews()
        setListeners(binding)
        setAdapters()
    }

    private fun fetchCatData() {
        viewModel.response.observe(this) { response ->
            when (response) {
                is NetworkStatus.Success -> {
                    loadCatImage(binding, response)
                    showOnResultViews()

                    Log.i(LOGGER_TAG, SUCCESS_STATUS)
                }

                is NetworkStatus.Error -> {
                    showErrorButton(binding)
                    showOnResultViews()

                    Log.i(LOGGER_TAG, ERROR_STATUS)
                    Log.e(LOGGER_TAG, response.message.toString())
                }
            }
        }
    }

    private fun fetchAllTags() = viewModel.getAllTags()

    private fun fetchRandomCat() = viewModel.getRandomCat()

    private fun setListeners(binding: ActivityCatGeneratorBinding) {
        setCheckboxListener(binding)
        setEditTextListener(binding)
        setSliderListener(binding)
        setButtonListener(binding)
    }

    private fun setAdapters() {
        setTagAdapter()
        setFilterAdapter()
        setColorAdapter()
    }

    private fun setCheckboxListener(binding: ActivityCatGeneratorBinding) {
        binding.apply {
            cbAddText.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    llExtraSection.showView()
                } else {
                    llExtraSection.hideView()
                    tvChooseColor.clearText()
                    etCatDescription.clearText()
                    sliderTextSize.value = DEFAULT_SLIDER_VALUE
                }
            }
        }
    }

    private fun setEditTextListener(binding: ActivityCatGeneratorBinding) {
        binding.etCatDescription.doOnTextChanged { text, _, _, _ ->
            textItem = text.toString()
        }
    }

    private fun setSliderListener(binding: ActivityCatGeneratorBinding) {
        binding.sliderTextSize.addOnChangeListener { _, value, _ ->
            optionItems[SIZE_KEY] = value.toInt().toString()
        }
    }

    private fun setButtonListener(binding: ActivityCatGeneratorBinding) {
        binding.apply {
            btnGiveMeCatDefault.setOnClickListener {
                if (tvChooseTag.text.isEmpty()) {
                    showErrorToast(
                        this@CatGeneratorActivity,
                        resources.getString(R.string.empty_tag_item_error)
                    )
                } else {
                    showOnProgressViews()
                    fetchCatByOptions()
                }
            }
        }
    }

    private fun setTagAdapter() {
        viewModel.tags.observe(this) { tags ->
            val adapter = tags.data?.toTypedArray()?.let {
                provideAdapter(it)
            }

            binding.apply {
                tvChooseTag.setAdapter(adapter)
                tvChooseTag.setOnItemClickListener { _, _, position, _ ->
                    tagItem = adapter?.getItem(position)
                }
            }
        }
    }

    private fun setFilterAdapter() {
        val adapter = provideAdapter(
            resources.getStringArray(R.array.cat_filters)
        )

        binding.apply {
            tvChooseFilter.setAdapter(adapter)
            tvChooseFilter.setOnItemClickListener { _, _, position, _ ->
                optionItems[FILTER_KEY] = adapter.getItem(position)
            }
        }
    }

    private fun setColorAdapter() {
        val adapter = provideAdapter(
            resources.getStringArray(R.array.cat_colors)
        )

        binding.apply {
            tvChooseColor.setAdapter(adapter)
            tvChooseColor.setOnItemClickListener { _, _, position, _ ->
                optionItems[COLOR_KEY] = adapter.getItem(position)
            }
        }
    }

    private fun provideAdapter(items: Array<String>): ArrayAdapter<String> {
        return ArrayAdapter(
            this,
            R.layout.item_dropdown_menu,
            items
        )
    }

    private fun fetchCatByOptions() {
        if (textItem?.isEmpty() == true)
            viewModel.getCatByOptions(tagItem, DEFAULT_TEXT_ITEM_VALUE, optionItems)
        else
            viewModel.getCatByOptions(tagItem, textItem, optionItems)
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

    private fun showOnProgressViews() {
        binding.pbLoadingImage.showView()
        binding.pbLoadingButton.showView()
        binding.btnGiveMeCatDefault
            .setBackgroundResource(R.drawable.view_bottom_button_loading)
    }

    private fun showOnResultViews() {
        binding.pbLoadingImage.hideView()
        binding.pbLoadingButton.hideView()
        binding.btnGiveMeCatDefault
            .setBackgroundResource(R.drawable.view_bottom_button_default)
    }

    private fun showErrorButton(binding: ActivityCatGeneratorBinding) {
        binding.btnGiveMeCatDefault.hideView()
        binding.btnGiveMeCatError.showView()

        lifecycleScope.launch {
            delay(ERROR_DELAY)
            binding.btnGiveMeCatError.hideView()
            binding.btnGiveMeCatDefault.showView()
        }
    }

    private fun showErrorToast(context: Context, toast: String) =
        Toast.makeText(context, toast, Toast.LENGTH_LONG).show()

    private companion object {
        const val LOGGER_TAG = "CatGeneratorActivity"
        const val SUCCESS_STATUS = "Network Status: SUCCESS"
        const val ERROR_STATUS = "Network Status: ERROR"

        const val ERROR_DELAY = 3000L
        const val DEFAULT_SLIDER_VALUE = 0F
        const val DEFAULT_TAG_ITEM_VALUE = ""
        const val DEFAULT_TEXT_ITEM_VALUE = " "

        const val FILTER_KEY = "filter"
        const val SIZE_KEY = "size"
        const val COLOR_KEY = "color"
    }
}