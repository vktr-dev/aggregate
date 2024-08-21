package dev.jokku.newsdata

import dev.jokku.aggregate.data.local.database.NewsDao
import dev.jokku.newsapi.RemoteDataSource
import dev.jokku.newsdata.model.ArticlesResponse
import dev.jokku.aggregate.data.sync.changeListSync
import kotlinx.coroutines.flow.Flow

interface NewsRepository : dev.jokku.aggregate.data.sync.Syncable {
    fun getLocalTopHeadlines(country: dev.jokku.aggregate.data.CountryCode): Flow<ArticlesResponse>
    fun getFavoriteTopHeadlines(
        countries: Set<dev.jokku.aggregate.data.CountryCode>,
        categories: Set<dev.jokku.aggregate.data.CategoryCode>
    ): Flow<List<ArticlesResponse>>

    fun observeRandomArticles(): Flow<Set<String>>
}

class DefaultNewsRepository @javax.inject.Inject constructor(
    private val remote: RemoteDataSource,
    private val local: NewsDao
) : NewsRepository {


    override fun getLocalTopHeadlines(country: dev.jokku.aggregate.data.CountryCode): Flow<ArticlesResponse> {
        local.observeFavoriteTopHeadlines()
    }

    override fun getFavoriteTopHeadlines(
        countries: Set<dev.jokku.aggregate.data.CountryCode>,
        categories: Set<dev.jokku.aggregate.data.CategoryCode>
    ): Flow<List<ArticlesResponse>> {
        TODO("Not yet implemented")
    }

    override fun observeRandomArticles(): Flow<Set<String>> {
        TODO("Not yet implemented")
    }

    // In this case synchronizer - is a SyncWorker class
    override suspend fun syncWith(synchronizer: dev.jokku.aggregate.data.sync.Synchronizer) =
        synchronizer.changeListSync(
            changeListFetcher = { request ->
                remote.getTopHeadlineArticles(request)
            },
            versionUpdater = { latestVersion ->
                copy(newsResourceVersion = latestVersion)
            },
            modelDeleter = local::delete
            modelUpdater = { changedIds ->
                val networkNewsResources = network.getNewsResources(ids = changedIds)

                // Order of invocation matters to satisfy id and foreign key constraints!

                topicDao.insertOrIgnoreTopics(
                    topicEntities = networkNewsResources
                        .map(NetworkNewsResource::topicEntityShells)
                        .flatten()
                        .distinctBy(TopicEntity::id)
                )
                newsResourceDao.upsertNewsResources(
                    newsResourceEntities = networkNewsResources
                        .map(NetworkNewsResource::asEntity)
                )
                newsResourceDao.insertOrIgnoreTopicCrossRefEntities(
                    newsResourceTopicCrossReferences = networkNewsResources
                        .map(NetworkNewsResource::topicCrossReferences)
                        .distinct()
                        .flatten()
                )
            }
        )

//    override fun getFavoriteTopHeadlines(
//        countries: Set<CountryCode>,
//        categories: Set<CategoryCode>
//    ): Flow<List<NewsResponse>> {
//        return try {
//            val response = remote.getTopHeadlineArticles(request)
//            if (response.isSuccess) {
//                Result.Success(actualData = response)
//            } else { // We have error type of response here, it means that the database can't have this data either
//                Result.Failure(
//                    message = UiErrorMessage(
//                        text = if (response.errorMessage != null) {
//                            UiText.DynamicString(response.errorMessage)
//                        } else {
//                            UiText.StringResource(R.string.no_articles_found)
//                        }
//                    ),
//                    cachedData = response
//                )
//            }
//        } catch (e: ResponseException) { // 300, 400, 500
////            TODO("add cache call result to data")
//            Result.Failure(
//                message = UiErrorMessage(
//                    text = UiText.DynamicString(e.response.status.description)
//                ),
//                cachedData = RemoteNewsResponse(status = "")
//            )
//        } catch (e: Exception) {
////            TODO("add cache call result to data")
//            Result.Failure(
//                message = UiErrorMessage(
//                    text = UiText.DynamicString(e.message ?: UNKNOWN_ERROR)
//                ),
//                cachedData = RemoteNewsResponse(status = "")
//            )
//        }
//    }
}