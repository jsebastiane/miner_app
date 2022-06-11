package sebastian.company.min3rapp.domain.repository

import sebastian.company.min3rapp.data.remote.dto.articles.ArticlesDto

interface NewsRepository {

//    suspend fun getArticles(keyword: String, date: String): ArticlesDto
    suspend fun getArticles(keyword: String, date: String): ArticlesDto


}