package com.amirhusseinsoori.hyperlink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.amirhusseinsoori.hyperlink.data.extention.createFileFromUri
import com.amirhusseinsoori.hyperlink.data.extention.getMessage
import com.amirhusseinsoori.hyperlink.ui.component.LifeCycleCompose
import com.amirhusseinsoori.hyperlink.ui.main.MainScreen
import com.amirhusseinsoori.hyperlink.ui.theme.HyperLinkTheme
import com.amirhusseinsoori.hyperlink.ui.theme.primary3
import org.apache.commons.io.FileUtils
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.InputStream
import java.util.Date


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: HyperViewModel = koinViewModel()

            HyperLinkTheme {


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LifeCycleCompose(onResume = {
                        intent.getMessage(insertDetails = {
                            val (details, type) = it
                            if (type == "img") {
                                val path = contentResolver.createFileFromUri(
                                    this,
                                    Date().time.toString(),
                                    details.toUri()
                                )?.path

                                viewModel.insertData(
                                    path, type, ""
                                )
                            } else {
                                viewModel.insertData(details, type, "")
                            }


                        })


                    }, onStart = { }, onStop = {})

                    MainScreen(viewModel = viewModel)


                }
            }
        }
    }


}






