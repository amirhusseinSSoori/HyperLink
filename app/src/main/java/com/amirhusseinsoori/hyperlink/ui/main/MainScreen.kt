package com.amirhusseinsoori.hyperlink.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.amirhusseinsoori.hyperlink.HyperViewModel
import com.amirhusseinsoori.hyperlink.ui.theme.primary3


@Composable
fun MainScreen(viewModel: HyperViewModel) {
    val list by viewModel.stateFlow.collectAsState(initial = emptyList())
    Column {
        list.let { data ->
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(data) {
                    MessageItem(
                        text = "${it.title}",
                        type = it.type ?: "",
                        onclick = {
                            viewModel.deleteMessageById(id = it.id)
                        })
                }
            }
        }

    }

}

@Composable
fun MessageItem(text: String, type: String, onclick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .background(primary3)
            .clickable {
                onclick()
            }

    ) {
        if (type == "txt") {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 10.dp),
                text = text,
                textAlign = TextAlign.End,
                color = Color.Black
            )
        } else if (type == "img") {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                model = text.toUri(),
                contentDescription = null,
            )
        }


    }
}
