package sebastian.company.min3rapp.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import sebastian.company.min3rapp.common.Resource
import sebastian.company.min3rapp.data.remote.dto.articles.toArticle
import sebastian.company.min3rapp.domain.model.Article
import sebastian.company.min3rapp.domain.repository.NewsRepository
import java.io.IOException
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
){

    //Is this called by default? Is that how operator fun invoke works
    //Cannot put anything specific like certain keywords or dates as we can use this usecase for
    //other fragments that gather articles
    operator fun invoke(keyword: String, date: String): Flow<Resource<List<Article>>> = flow {
        try {
            //emit works with flow
            //Change date
            emit(Resource.Loading<List<Article>>())
            val articles = repository.getArticles(keyword = keyword, date = date).articleDtos!!.map {
                it.toArticle()
            }
            emit(Resource.Success(articles))
        }catch (e: HttpException){
            emit(Resource.Error<List<Article>>(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error<List<Article>>(e.localizedMessage ?: "Cannot connect to server"))
        }
    }
}