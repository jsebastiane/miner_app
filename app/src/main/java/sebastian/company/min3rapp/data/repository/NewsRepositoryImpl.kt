package sebastian.company.min3rapp.data.repository

import sebastian.company.min3rapp.data.remote.dto.articles.ArticlesDto
import sebastian.company.min3rapp.data.remote.NewsApi
import sebastian.company.min3rapp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
): NewsRepository {

    override suspend fun getArticles(keyword: String, date: String): ArticlesDto {
        return api.getNews(keyword, date)
    }

}