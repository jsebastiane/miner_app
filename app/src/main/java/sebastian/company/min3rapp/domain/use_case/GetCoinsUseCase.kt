package sebastian.company.min3rapp.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import sebastian.company.min3rapp.common.Resource
import sebastian.company.min3rapp.data.remote.dto.crypto_data.toCoin
import sebastian.company.min3rapp.domain.model.Coin
import sebastian.company.min3rapp.domain.repository.CoinRepository
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
){


    operator fun invoke(): Flow<Resource<List<Coin>>> = flow{
        try{
            emit(Resource.Loading<List<Coin>>())
            val coins = repository.getCoins().map {
                it.toCoin()
            }
            emit(Resource.Success(coins))
        }catch (e: HttpException){
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "An unexpected error occurred"))
        }catch (e: IOException){
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "Cannot connect to server"))
        }
    }
}