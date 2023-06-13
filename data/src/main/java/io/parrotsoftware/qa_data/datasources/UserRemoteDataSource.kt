package io.parrotsoftware.qa_data.datasources

import io.parrotsoftware.qa_data.domain.Credentials
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.domain.Store

interface UserRemoteDataSource {
     suspend fun login(email: String, password: String): RepositoryResult<Nothing>

    suspend fun userExists(): RepositoryResult<Boolean>

    suspend fun getCredentials(): RepositoryResult<Credentials>

    suspend fun getStore(): RepositoryResult<Store>
}