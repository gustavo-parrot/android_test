package io.parrotsoftware.qa_data.datasources

import io.parrotsoftware.qa_data.domain.Credentials
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.domain.Store

interface UserLocalDataSource {

    suspend fun getCredentials(): RepositoryResult<Credentials>

    suspend fun getStore(): RepositoryResult<Store>

    suspend fun userExists(): RepositoryResult<Boolean>

}