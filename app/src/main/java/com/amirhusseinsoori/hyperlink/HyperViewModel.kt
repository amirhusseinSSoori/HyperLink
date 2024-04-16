package com.amirhusseinsoori.hyperlink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HyperViewModel(val repository: HyperRepository) :ViewModel() {

    init {
        showList()
    }

    private val mutableStateFlow:MutableStateFlow<List<String>> = MutableStateFlow<List<String>>(emptyList())
    val stateFlow:StateFlow<List<String>> = mutableStateFlow.asStateFlow()
     fun showList(){
        viewModelScope.launch {
            mutableStateFlow.value = repository.showList()
        }
    }
}