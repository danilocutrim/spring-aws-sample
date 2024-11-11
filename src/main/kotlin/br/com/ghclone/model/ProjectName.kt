package br.com.ghclone.model

@JvmInline
value class ProjectName(val name: String) {
    init {
        require(name.isNotBlank()) { "Project name cannot be blank" }
        require(name.length <= 50) { "Project name cannot be longer than 50 characters" }
        require(!name.contains(Regex("[^a-zA-Z0-9-_]"))) { "Project name can only contain letters, numbers, hyphens, and underscores" }
    }
}
