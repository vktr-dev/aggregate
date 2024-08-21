package dev.jokku.newsapi.model

import dev.jokku.aggregate.data.local.database.entity.ArticleEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkArticle(
    /**
     * The author of the article
     */
    @SerialName("author")
    val author: String,
    /**
     * The unformatted content of the article, where available. This is truncated to 200 chars.
     */
    @SerialName("content")
    val content: String,
    /**
     * A description or snippet from the article.
     */
    @SerialName("description")
    val description: String,
    /**
     * The date and time that the article was published, in UTC (+000)
     */
    @SerialName("publishedAt")
    val publishedAt: String,
    /**
     * The source this article came from.
     */
    @SerialName("source")
    val source: NetworkArticleSource,
    /**
     * The headline or title of the article.
     */
    @SerialName("title")
    val title: String,
    /**
     * The direct URL to the article.
     */
    @SerialName("url")
    val url: String,
    /**
     * The URL to a relevant image for the article.
     */
    @SerialName("urlToImage")
    val urlToImage: String
) : dev.jokku.aggregate.data.mapper.FromRemoteMapper<dev.jokku.aggregate.data.local.database.entity.ArticleEntity> {
    override fun asEntity() = dev.jokku.aggregate.data.local.database.entity.ArticleEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.asEntity(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}