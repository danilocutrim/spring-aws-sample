package br.com.ghclone.exception


data class InvalidFieldsException(val errors: List<FieldError>, val reason: String) {
    class Builder {
        private val errors = mutableListOf<FieldError>()
        private var reason = ""

        fun addError(field: String, message: String): Builder {
            errors.add(FieldError(field, message))
            return this
        }

        fun reason(reason: String): Builder {
            this.reason = reason
            return this
        }

        fun build(): InvalidFieldsException {
            return InvalidFieldsException(errors, reason)
        }

    }
}


data class FieldError(
    val field: String,
    val message: String
)
