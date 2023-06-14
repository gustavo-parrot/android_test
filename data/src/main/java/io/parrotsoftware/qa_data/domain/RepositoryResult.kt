package io.parrotsoftware.qa_data.domain


data class RepositoryError(
    val code: String,
    val message: String
)

data class RepositoryResult<T>(
    val result: T? = null,
    val error: RepositoryError? = null,
) {
    constructor(errorCode: String, errorMessage: String) : this(
        error = RepositoryError(
            errorCode,
            errorMessage
        )
    )

    val isError: Boolean
        get() = error != null

    val requiredResult: T
        get() = result!!

    val requiredError: RepositoryError
        get() = error!!
}