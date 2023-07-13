package com.jokku.aggregate.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jokku.aggregate.data.local.database.DatabaseConstants.FAVORITE_TOP_HEADLINES_RESPONSES

@Entity(tableName = FAVORITE_TOP_HEADLINES_RESPONSES)
data class LocalFavoriteTopHeadlinesResponse(
    val totalResults: Int,
    val responseTime: Long,
    val countryId: String = "",
    val categoryId: String = "",
    val sourceId: String = "",
    val query: String = "",
    @PrimaryKey
    val responseId: String = generateResponseId(countryId, categoryId, sourceId, query)
) {
    companion object {
        private fun generateResponseId(
            countryId: String,
            categoryId: String,
            sourceId: String,
            query: String
        ): String = "$countryId$categoryId$sourceId$query"
    }
}