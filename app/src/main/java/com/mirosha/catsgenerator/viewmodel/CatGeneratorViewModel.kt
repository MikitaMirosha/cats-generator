package com.mirosha.catsgenerator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mirosha.catsgenerator.model.CatResponse
import com.mirosha.catsgenerator.repo.CatRepository
import com.mirosha.catsgenerator.utils.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatGeneratorViewModel
@Inject constructor(
    private val catRepository: CatRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<NetworkStatus<CatResponse>> = MutableLiveData()
    val response: LiveData<NetworkStatus<CatResponse>> = _response

    private val _tags: MutableLiveData<NetworkStatus<MutableList<String>>> = MutableLiveData()
    val tags: LiveData<NetworkStatus<MutableList<String>>> = _tags

    fun getRandomCat() = viewModelScope.launch {
        catRepository.getRandomCat().collect { values ->
            _response.value = values
        }
    }

    fun getAllTags() = viewModelScope.launch {
        catRepository.getAllTags().collect { values ->
            _tags.value = values
        }
    }

    fun getRandomCatByTag(tag: String) = viewModelScope.launch {
        catRepository.getRandomCatByTag(tag).collect { values ->
            _response.value = values
        }
    }
}