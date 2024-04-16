package com.amirhusseinsoori.hyperlink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HyperViewModel(val repository: HyperRepository) : ViewModel() {


    private val mutableStateFlow = MutableStateFlow<List<String>>(emptyList())
    val stateFlow: StateFlow<List<String>> = mutableStateFlow.asStateFlow()

    init {
        showList()
    }


    private fun showList() {
        viewModelScope.launch {
            mutableStateFlow.value = repository.showList()
        }
    }
}