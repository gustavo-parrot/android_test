package io.parrotsoftware.qa_data.datasources.impl

import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.managers.UserManager
import io.parrotsoftware.qa_network.domain.requests.ApiAuthRequest
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.services.ParrotApi

class UserRemoteDataSourceImpl(
    private val userManager: UserManager,
    private val networkInteractor: NetworkInteractor
) : UserRemoteDataSource {
    override suspend fun login(email: String, password: String): RepositoryResult<Nothing> {
        val responseAuth = networkInteractor.safeApiCall {
            ParrotApi.service.auth(ApiAuthRequest(email, password))
        }
        if (responseAuth.isError)
            return RepositoryResult(
                errorCode = responseAuth.requiredError.requiredErrorCode,
                errorMessage = responseAuth.requiredError.requiredErrorMessage
            )

        val credentials = responseAuth.requiredResult
        val responseUser = networkInteractor.safeApiCall {
            ParrotApi.service.getMe("Bearer ${credentials.accessToken}")
        }

        if (responseUser.isError) {
            return RepositoryResult(
                errorCode = responseUser.requiredError.requiredErrorCode,
                errorMessage = responseUser.requiredError.requiredErrorMessage
            )
        }

        if (responseUser.requiredResult.result.stores.isEmpty()) {
            return RepositoryResult(
                errorCode = "",
                errorMessage = "Store Not Found"
            )
        }

        val apiCredentials = responseAuth.requiredResult
        val apiUser = responseUser.requiredResult.result
        val apiStore = apiUser.stores.first()

        userManager.saveCredentials(apiCredentials.accessToken, apiCredentials.refreshToken)
        userManager.saveStore(apiStore.uuid, apiStore.name)

        return RepositoryResult()
    }

}