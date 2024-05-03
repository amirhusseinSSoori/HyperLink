package com.amirhusseinsoori.hyperlink

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amirhusseinsoori.hyperlink.ui.component.LifeCycleCompose
import com.amirhusseinsoori.hyperlink.ui.theme.HyperLinkTheme
import com.amirhusseinsoori.hyperlink.ui.theme.primary3
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.log

class MainActivity : ComponentActivity() {

    val a = ""
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

                    LifeCycleCompose(onResume = {
                        getMessage(insertDetails = {
                            val (details, type) = it
                            viewModel.insertData(details, type, "")

                        })


                    }, onStart = { }, onStop = {})

                    val list by viewModel.stateFlow.collectAsState(initial = emptyList())
                    Column {
                        list.let { data ->
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(data) {
                                    MessageItem(text = "${it.title}", type = it.type ?: "")
                                }
                            }
                        }

                    }

                }
            }
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var cursor: Cursor? = null

        try {
            cursor = contentResolver.query(contentUri, projection, null, null, null)
            if (cursor != null) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }

        return null
    }

    private fun getMessage(insertDetails: (Pair<String, String>) -> Unit) {

        geTextMessage()?.let {
            insertDetails(Pair(it.toString(), "txt"))
        } ?: run {
            if (intent?.action == Intent.ACTION_SEND) {
                (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                    insertDetails(Pair(it.toString(), "img"))
                }

            }
        }


    }


    private fun geTextMessage(): CharSequence? {
        val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        return text?.let {
            it
        } ?: run {
            intent.getCharSequenceExtra(Intent.EXTRA_TEXT)
        }


    }

}


@Composable
fun MessageItem(text: String, type: String) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .background(primary3)

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
                model = text,
                contentDescription = null,
            )
        }


    }
}









