package io.parrotsoftware.qa_data.repositories

import io.parrotsoftware.qa_data.RepositoryResultD
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import javax.inject.Inject

class UserRepositoryD
@Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) {

    suspend fun userExists(): RepositoryResultD<Boolean> =
        userRemoteDataSource.userExists()

    suspend fun login(email: String, password: String): RepositoryResultD<Nothing> =
        userRemoteDataSource.login(email, password)


}