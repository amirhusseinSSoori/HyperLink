package com.amirhusseinsoori.hyperlink.data.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.amirhusseinsoori.sqldeLightHyperLink.HyperLinkDatabase


interface DatabaseDriverFactory {
    fun createDriver(context: Context): SqlDriver
}

class DatabaseDriverFactoryImp: DatabaseDriverFactory {
    override fun createDriver(context: Context): SqlDriver {
        return AndroidSqliteDriver(HyperLinkDatabase.Schema, context, "hyperLinkDatabase.db")
    }
}