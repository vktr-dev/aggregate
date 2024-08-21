package dev.jokku.ui

import kotlin.String

data class UiArticle(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: dev.jokku.aggregate.presentation.model.UiArticleSource = dev.jokku.aggregate.presentation.model.UiArticleSource(),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    val bookmarked: Boolean = false
)