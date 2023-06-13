package io.parrotsoftware.qa_data.datasources.impl

import io.parrotsoftware.qa_data.datasources.UserLocalDataSource
import io.parrotsoftware.qa_data.domain.Credentials
import io.parrotsoftware.qa_data.domain.RepositoryResult
import io.parrotsoftware.qa_data.domain.Store
import io.parrotsoftware.qa_data.managers.UserManager
import javax.inject.Inject

class UserLocalDataSourceImpl
    @Inject constructor(private val userManager: UserManager)
    : UserLocalDataSource {

    override suspend fun getCredentials(): RepositoryResult<Credentials> {
        return RepositoryResult(
            Credentials(
                userManager.getAccess(), userManager.getRefresh()
            )
        )
    }

    override suspend fun getStore(): RepositoryResult<Store> {
        return RepositoryResult(
            Store(
                userManager.getStoreUuid(), userManager.getStoreName()
            )
        )
    }

    override suspend fun userExists(): RepositoryResult<Boolean>
    = RepositoryResult(userManager.isAuth())

}