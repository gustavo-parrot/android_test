package io.parrotsoftware.qa_data.repositories

import io.parrotsoftware.qa_data.domain.Credentials
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.domain.Store
import io.parrotsoftware.qa_data.managers.UserManager
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userManagerD: UserManager
    ) {

    suspend fun userExists(): RepositoryResult<Boolean> =
        userRemoteDataSource.userExists()

    suspend fun login(email: String, password: String): RepositoryResult<Nothing> =
        userRemoteDataSource.login(email, password)

    suspend fun getCredentials(): RepositoryResult<Credentials> {
        return RepositoryResult(
            Credentials(
            userManagerD.getAccess(), userManagerD.getRefresh()
        )
        )
    }

     suspend fun getStore(): RepositoryResult<Store> {
        return RepositoryResult(
            Store(
            userManagerD.getStoreUuid(), userManagerD.getStoreName()
        )
        )
    }


}