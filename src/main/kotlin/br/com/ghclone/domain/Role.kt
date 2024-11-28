package br.com.ghclone.domain


//@JvmInline
//value class Pk(val value: String)
//
//inline fun <reified T> T.pk() = Pk(T::class.?: error("Class name not found"))
//
//@JvmInline
//value class Sk(val value: String)
//
//
//sealed class Role(val pk: Pk, val sk: Sk, val createdAt: String){
//    data class Admin(val pk: Pk, val sk: Sk, val createdAt: String): Role(pk, sk, createdAt)
//    data class User(val pk: Pk, val sk: Sk, val createdAt: String): Role(pk, sk, createdAt)
//    data class Guest(val pk: Pk, val sk: Sk, val createdAt: String): Role(pk, sk, createdAt)
//}