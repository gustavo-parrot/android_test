package io.parrotsoftware.qa_network.interactors.impl

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Provides
import io.parrotsoftware.qa_network.domain.APIError
import io.parrotsoftware.qa_network.domain.NetworkError
import io.parrotsoftware.qa_network.domain.NetworkErrorType
import io.parrotsoftware.qa_network.domain.NetworkResult
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
class NetworkInteractorImpl : NetworkInteractor {

    override suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): NetworkResult<T> = withContext(dispatcher) {
        try {
            val response = apiCall.invoke()
            NetworkResult(result = response)
        } catch (throwable: Throwable) {
            NetworkResult(networkError = createError(throwable))
        }
    }

    @Provides
    private fun createError(throwable: Throwable): NetworkError {
        return when (throwable) {
            is IOException -> {
                NetworkError(
                    NetworkErrorType.CONNECTION_ERROR,
                    throwable.message,
                    NetworkErrorType.CONNECTION_ERROR.name
                )
            }
            is HttpException -> {
                val bodyResponse: String? = throwable.response()?.errorBody()?.string()
                val apiError = parseErrorBody(bodyResponse)
                val codeError = apiError?.code
                NetworkError(
                    NetworkErrorType.API_ERROR,
                    bodyResponse,
                    codeError ?: throwable.code().toString(),
                    apiError
                )
            }
            is JsonDataException -> {
                NetworkError(NetworkErrorType.API_ERROR, throwable.message)
            }
            else -> {
                NetworkError(NetworkErrorType.UNKNOWN_ERROR, throwable.message)
            }
        }
    }

    private fun parseErrorBody(bodyResponse: String?): APIError? {
        return try {
            bodyResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<APIError> = moshi.adapter(APIError::class.java)
                return jsonAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }
}
