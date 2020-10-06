package com.dongldh.carrot.data

data class User(
    var uid: String? = null,
    val nickname: String? = null,
    val profileUrl: String? = null,
    val regionIdAll: List<Long> = mutableListOf(),
    val regionStringAll: List<String> = mutableListOf(),
    val regionIdSelected: Long? = null,
    val regionStringSelected: String? = null,
    val savedSentences: List<String> = mutableListOf(),
    val favoriteUsersUid: List<String> = mutableListOf(),
    val favoriteItemsId: List<Long> = mutableListOf()
){}

data class UserCreateAccountRequest(
    val email: String,
    val password: String,
    val nickName: String,
    val regionId: Long,
    val regionString: String,
    val profileImageUrl: String
) {}