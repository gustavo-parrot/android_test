package io.parrotsoftware.qa_data.repositories

import io.parrotsoftware.qa_data.datasources.UserLocalDataSource
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import io.parrotsoftware.qa_data.domain.Credentials
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.domain.Store
import javax.inject.Inject

class UserRepository
@Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {

    suspend fun userExists(): RepositoryResult<Boolean> =
        userLocalDataSource.userExists()

    suspend fun login(email: String, password: String): RepositoryResult<Nothing> =
        userRemoteDataSource.login(email, password)

    suspend fun getCredentials(): RepositoryResult<Credentials> =
        userLocalDataSource.getCredentials()


    suspend fun getStore(): RepositoryResult<Store> = userLocalDataSource.getStore()


}