package com.amirhusseinsoori.hyperlink.data

import android.net.Uri
import androidx.annotation.Keep

data class Image(
    val id: Long,
    val name: String,
    val uri: Uri
)
@Keep
enum class MessageType{
    IMAGE,TEXT
}