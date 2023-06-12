package io.parrotsoftware.qa_data.datasources

import io.parrotsoftware.qa_data.CredentialsD
import io.parrotsoftware.qa_data.RepositoryResultD
import io.parrotsoftware.qa_data.StoreD

interface UserRemoteDataSource {
     suspend fun login(email: String, password: String): RepositoryResultD<Nothing>

    suspend fun userExists(): RepositoryResultD<Boolean>

    suspend fun getCredentials(): RepositoryResultD<CredentialsD>

    suspend fun getStore(): RepositoryResultD<StoreD>
}