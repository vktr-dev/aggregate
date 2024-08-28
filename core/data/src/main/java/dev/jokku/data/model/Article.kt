package dev.jokku.data.model

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: ArticleSource,
    val title: String,
    val url: String,
    val urlToImage: String
)

data class ArticleSource(
    val id: String,
    val name: String
)