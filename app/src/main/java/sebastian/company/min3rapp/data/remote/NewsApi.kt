package sebastian.company.min3rapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import sebastian.company.min3rapp.data.remote.dto.articles.ArticlesDto
import sebastian.company.min3rapp.common.Constants

interface NewsApi {

    //Only get RECENT crypto news!
    //Date format '2022-03-01'
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") keyword: String? = null,
        @Query("from") date: String? = null,
        @Query("apiKey") apiKey: String = Constants.NEWSAPI_KEY
): ArticlesDto

}