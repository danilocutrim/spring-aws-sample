package br.com.ghclone.model

@JvmInline
value class Endpoint(val uri: String) {
    init {
        require(uri.isNotBlank()) { "Endpoint URI cannot be blank" }
        require(uri.length <= 255) { "Endpoint URI cannot be longer than 255 characters" }
        require(!uri.contains(Regex("[^a-zA-Z0-9-_()]"))) { "Project name can only contain letters, numbers, hyphens, underscores, and parentheses" }
    }
}
