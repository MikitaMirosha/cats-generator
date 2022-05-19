package com.mirosha.catsgenerator.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mirosha.catsgenerator.R
import com.mirosha.catsgenerator.databinding.ActivityCatsGeneratorBinding
import com.mirosha.catsgenerator.utils.ActivityUtils.getViewBinding

class CatsGeneratorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatsGeneratorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(R.layout.activity_cats_generator)
    }
}