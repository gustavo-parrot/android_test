package io.parrotsoftware.qa_data.datasources.impl

import io.parrotsoftware.qa_data.CredentialsD
import io.parrotsoftware.qa_data.RepositoryResultD
import io.parrotsoftware.qa_data.StoreD
import io.parrotsoftware.qa_data.UserManagerD
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_network.domain.requests.ApiAuthRequest
import io.parrotsoftware.qa_network.interactors.NetworkInteractor
import io.parrotsoftware.qa_network.services.ParrotApi

class UserRemoteDataSourceImpl(
    private val userManager: UserManagerD,
    private val networkInteractor: NetworkInteractor
) : UserRemoteDataSource {
    override suspend fun login(email: String, password: String): RepositoryResultD<Nothing> {
        val responseAuth = networkInteractor.safeApiCall {
            ParrotApi.service.auth(ApiAuthRequest(email, password))
        }
        if (responseAuth.isError)
            return RepositoryResultD(
                errorCode = responseAuth.requiredError.requiredErrorCode,
                errorMessage = responseAuth.requiredError.requiredErrorMessage
            )

        val credentials = responseAuth.requiredResult
        val responseUser = networkInteractor.safeApiCall {
            ParrotApi.service.getMe("Bearer ${credentials.accessToken}")
        }

        if (responseUser.isError) {
            return RepositoryResultD(
                errorCode = responseUser.requiredError.requiredErrorCode,
                errorMessage = responseUser.requiredError.requiredErrorMessage
            )
        }

        if (responseUser.requiredResult.result.stores.isEmpty()) {
            return RepositoryResultD(
                errorCode = "",
                errorMessage = "Store Not Found"
            )
        }

        val apiCredentials = responseAuth.requiredResult
        val apiUser = responseUser.requiredResult.result
        val apiStore = apiUser.stores.first()

        userManager.saveCredentials(apiCredentials.accessToken, apiCredentials.refreshToken)
        userManager.saveStore(apiStore.uuid, apiStore.name)

        return RepositoryResultD()
    }

    override suspend fun userExists(): RepositoryResultD<Boolean> {
        return RepositoryResultD(userManager.isAuth())
    }

    override suspend fun getCredentials(): RepositoryResultD<CredentialsD> {
        return RepositoryResultD(CredentialsD(
            userManager.getAccess(), userManager.getRefresh()
        ))
    }

    override suspend fun getStore(): RepositoryResultD<StoreD> {
        return RepositoryResultD(StoreD(
            userManager.getStoreUuid(), userManager.getStoreName()
        ))
    }
}