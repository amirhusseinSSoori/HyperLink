package com.amirhusseinsoori.hyperlink

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirhusseinsoori.hyperlink.data.repository.HyperRepository
import hyperLinkDatabase.Messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HyperViewModel(private val repository: HyperRepository) : ViewModel() {


    private val mutableStateFlow = MutableStateFlow<List<Messages>>(emptyList())
    val stateFlow: StateFlow<List<Messages>> = mutableStateFlow.asStateFlow()

    init {
        showList()
    }

    var images by mutableStateOf(emptyList<Image>())
        private set

    fun updateImages(list:List<Image>){
        this.images =list

    }

    private fun showList() {
        viewModelScope.launch {
            repository.getAll().collect(){
                mutableStateFlow.value = it
            }

        }
    }

    fun deleteMessageById(id:Long){
        repository.deleteById(id = id)
    }

     fun insertData(
        title: String?,
        type: String?,
        date: String?,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(title, type, date)
        }
    }


}