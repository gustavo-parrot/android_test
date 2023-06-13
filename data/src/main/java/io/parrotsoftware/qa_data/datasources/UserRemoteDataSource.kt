package io.parrotsoftware.qa_data.datasources

import io.parrotsoftware.qa_data.domain.RepositoryResult

interface UserRemoteDataSource {
    suspend fun login(email: String, password: String): RepositoryResult<Nothing>

}