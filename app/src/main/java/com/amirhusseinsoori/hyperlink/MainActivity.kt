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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.amirhusseinsoori.hyperlink.ui.component.LifeCycleCompose
import com.amirhusseinsoori.hyperlink.ui.theme.HyperLinkTheme
import com.amirhusseinsoori.hyperlink.ui.theme.primary3
import org.apache.commons.io.FileUtils
import org.koin.androidx.compose.koinViewModel
import java.io.File
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
                        getMessage(insertDetails = {
                            val (details, type) = it
                            if (type == "img") {


                                viewModel.insertData(
                                    createFileFromUri(
                                        Date().time.toString(),
                                        details.toUri()
                                    )?.path, type, ""
                                )
                            } else {
                                viewModel.insertData(details, type, "")
                            }


                        })


                    }, onStart = { }, onStop = {})

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
            }
        }
    }

    companion object {
        private val TAG = "myTag"

        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        const val KEY_FLASH = "sPrefFlashCamera"
        const val KEY_GRID = "sPrefGridCamera"
        const val KEY_HDR = "sPrefHDR"

        /** Helper function used to create a timestamped file */
        private fun createFile(
            baseFolder: File,
            format: String = "FILENAME",
            extension: String = PHOTO_EXTENSION
        ) = File(baseFolder, format + extension)
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

    private fun createFileFromUri(name: String, uri: Uri): File? {
        return try {
            val stream = contentResolver.openInputStream(uri)
            val file =
                File.createTempFile(
                    "${name}_${System.currentTimeMillis()}",
                    ".jpg",
                    cacheDir
                )
            FileUtils.copyInputStreamToFile(
                stream,
                file
            )  // Use this one import org.apache.commons.io.FileUtils
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
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



