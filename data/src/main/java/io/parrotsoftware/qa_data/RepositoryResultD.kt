package io.parrotsoftware.qa_data


data class RepositoryErrorD(
    val code: String,
    val message: String
)

data class RepositoryResultD<T>(
    val result: T? = null,
    val error: RepositoryErrorD? = null,
) {
    constructor(errorCode: String, errorMessage: String) : this(
        error = RepositoryErrorD(
            errorCode,
            errorMessage
        )
    )

    val isError: Boolean
        get() = error != null

    val requiredResult: T
        get() = result!!

    val requiredError: RepositoryErrorD
        get() = error!!
}