package com.amirhusseinsoori.hyperlink.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.amirhusseinsoori.sqldeLightHyperLink.HyperLinkDatabase
import hyperLinkDatabase.Messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

interface HyperRepository {
    fun showList(): List<String>

    fun insert(
        title: String?,
        type: String?,
        date: String?,
    )

    fun getAll(): Flow<List<Messages>>
}

class HyperRepositoryImp(db: HyperLinkDatabase) : HyperRepository {

    private val queries = db.hyperLinkQueries
    override fun showList(): List<String> {
        return listOf("apple", "orange")
    }

    override fun insert(
        title: String?,
        type: String?,
        date: String?,
    ) {
        queries.insertToMessage(
            title = title,
            type = type,
            create_date = date
        )
    }

    override fun getAll(): Flow<List<Messages>> {
        return queries.getAllMessage()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

}