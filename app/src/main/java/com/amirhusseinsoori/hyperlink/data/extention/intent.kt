package com.amirhusseinsoori.hyperlink.data.extention

import android.content.Intent
import android.net.Uri
import android.os.Parcelable

inline fun Intent.getMessage(insertDetails: (Pair<String, String>) -> Unit) {
    this.geTextMessage()?.let {
        insertDetails(Pair(it.toString(), "txt"))
    } ?: run {
        if (this?.action == Intent.ACTION_SEND) {
            (this.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
                insertDetails(Pair(it.toString(), "img"))
            }

        }
    }
}

fun Intent.geTextMessage(): CharSequence? {
    val text = this.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
    return text?.let {
        it
    } ?: run {
        this.getCharSequenceExtra(Intent.EXTRA_TEXT)
    }


}