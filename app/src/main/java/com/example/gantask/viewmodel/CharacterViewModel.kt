package com.example.gantask.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.gantask.rest.NetworkResponseHandler
import com.example.gantask.rest.data.BBCharactersData
import com.example.gantask.rest.fetcher.BaseFetcher
import com.example.gantask.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class CharacterViewModel(private val fetcher: BaseFetcher) : ViewModel() {

    val showLoading = ObservableBoolean()
    val characterList = MutableLiveData<List<BBCharactersData>>()
    val showError = SingleLiveEvent<String>()

    fun getCharacters() {
        showLoading.set(true)
        viewModelScope.launch {
            val result = fetcher.getAllCharacters()
            showLoading.set(false)
            when (result) {
                is NetworkResponseHandler.Success -> {
                    characterList.value = result.successData
                    showError.value = null
                }
                is NetworkResponseHandler.Error -> showError.value = result.exception.message
            }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun ImageView.loadImageFromURL(imgURL: String) {
            Glide.with(this)
                .load(imgURL)
                .into(this)
        }
    }
}