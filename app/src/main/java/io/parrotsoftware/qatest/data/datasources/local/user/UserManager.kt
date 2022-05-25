package io.parrotsoftware.qatest.data.datasources.local.user

interface UserManager {

    fun saveCredentials(access: String, refresh: String)

    fun getAccess(): String

    fun getRefresh(): String

    fun saveStore(uuid: String, name: String)

    fun getStoreUuid(): String

    fun getStoreName(): String

    fun isAuth(): Boolean
}