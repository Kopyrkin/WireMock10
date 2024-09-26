package ru.tinkoff.myupgradeapplication.network

object WikiModel {

    data class Result(val query: Query)

    data class Query (
        val searchinfo: SearchInfo,
        val search: List<Search>
    )

    data class Search (
        val ns: Long,
        val title: String,
        val pageid: Long,
        val size: Long,
        val wordcount: Long,
        val snippet: String,
        val timestamp: String
    )

    data class SearchInfo (
        val totalhits: Long
    )

}