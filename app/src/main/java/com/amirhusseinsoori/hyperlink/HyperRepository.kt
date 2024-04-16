package com.amirhusseinsoori.hyperlink

interface HyperRepository {
    fun showList():List<String>
}

class HyperRepositoryImp():HyperRepository{
    override fun showList(): List<String> {
        return listOf("apple","orange")
    }

}