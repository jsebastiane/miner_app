package sebastian.company.min3rapp.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import sebastian.company.min3rapp.common.Resource
import sebastian.company.min3rapp.data.remote.dto.crypto_data.toMarketData
import sebastian.company.min3rapp.domain.model.MarketData
import sebastian.company.min3rapp.domain.repository.GlobalCoinRepository
import java.io.IOException
import javax.inject.Inject

class GetGlobalDataUseCase @Inject constructor(
    private val repository: GlobalCoinRepository
) {

    operator fun invoke(): Flow<Resource<MarketData>> = flow{
        try{
            emit(Resource.Loading<MarketData>())
            val globalData = repository.getGlobalData().data.toMarketData()
            emit(Resource.Success(globalData))
        }catch (e: HttpException){
            emit(Resource.Error<MarketData>(e.localizedMessage ?: "An unexpected error occurred"))

        }catch (e: IOException){
            emit(Resource.Error<MarketData>(e.localizedMessage ?: "Cannot connect to server"))
        }
    }
}