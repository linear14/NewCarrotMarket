package com.dongldh.carrot.data

data class User(
    var uid: String? = null,
    val nickname: String,
    val profileUrl: String,
    val region: List<String>,
    val savedSentences: List<String> = mutableListOf(),
    val favoriteUsersUid: List<String> = mutableListOf(),
    val favoriteItemsId: List<Long> = mutableListOf()
){}

data class UserCreateAccountRequest(
    val email: String,
    val password: String,
    val nickName: String,
    val region: String,
    val profileImageUrl: String
) {

}