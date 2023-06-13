package io.parrotsoftware.qa_data.repositories

import io.parrotsoftware.qa_data.CredentialsD
import io.parrotsoftware.qa_data.RepositoryResultD
import io.parrotsoftware.qa_data.StoreD
import io.parrotsoftware.qa_data.UserManagerD
import io.parrotsoftware.qa_data.datasources.UserRemoteDataSource
import javax.inject.Inject

class UserRepositoryD
@Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userManagerD: UserManagerD
    ) {

    suspend fun userExists(): RepositoryResultD<Boolean> =
        userRemoteDataSource.userExists()

    suspend fun login(email: String, password: String): RepositoryResultD<Nothing> =
        userRemoteDataSource.login(email, password)

    suspend fun getCredentials(): RepositoryResultD<CredentialsD> {
        return RepositoryResultD(CredentialsD(
            userManagerD.getAccess(), userManagerD.getRefresh()
        ))
    }

     suspend fun getStore(): RepositoryResultD<StoreD> {
        return RepositoryResultD(StoreD(
            userManagerD.getStoreUuid(), userManagerD.getStoreName()
        ))
    }


}