package br.com.ghclone.enums

enum class PkPrefix(val prefix: String) {
    REPO("REPO#"),
    CONTENT("CTT#"),
    USER("USR#"),
    ROLE("ROL#");

    fun pk(key: String): String {
        require(key.isNotBlank()) { "Key cannot be empty" }
        return "$prefix$key"
    }

}