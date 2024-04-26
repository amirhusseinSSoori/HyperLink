package com.amirhusseinsoori.hyperlink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.amirhusseinsoori.hyperlink.ui.theme.HyperLinkTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HyperLinkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: HyperViewModel = koinViewModel()
                    val list by viewModel.stateFlow.collectAsState(initial = emptyList())
                    Column {
                        Button(onClick = {

                            viewModel.insertData("google", "video", "2019")

                        }) {


                        }
                        list.let { data ->
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(data) {
                                    Text(text = "title : ${it.title} , type :${it.type}, createDate : ${it.create_date}")
                                }
                            }
                        }

                    }

                }
            }
        }
    }
}

