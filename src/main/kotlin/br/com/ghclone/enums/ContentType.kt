package br.com.ghclone.enums

enum class ContentType(val key: String) {
    REPO("DIR"),
    FILE("FILE"),
    DIR("DIR"),
    EMPTY("PJCT");

    fun toKey(str:String) = key.plus(str)
}