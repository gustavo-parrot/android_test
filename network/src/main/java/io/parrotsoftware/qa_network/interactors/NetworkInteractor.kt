package io.parrotsoftware.qa_network.interactors

import io.parrotsoftware.qa_network.domain.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface NetworkInteractor {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> T
    ): NetworkResult<T>
}
