package dev.aggregate.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.aggregate.database.utils.DatabaseConstants.ARTICLE_SOURCE_
import dev.aggregate.database.utils.DatabaseConstants.TOP_HEADLINES_ARTICLES
import java.util.Date

@Entity(
    tableName = TOP_HEADLINES_ARTICLES
)
data class TopHeadlinesArticleEntity(
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "published_at") val publishedAt: Date?,
    @Embedded(prefix = ARTICLE_SOURCE_) val source: ArticleEntitySource?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "url_to_image") val urlToImage: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
