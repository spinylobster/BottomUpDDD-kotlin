package BottomupDDD.domain.user

import java.util.UUID

class User(val id: UserId, val userName: UserName, val name: FullName) {
    constructor(userName: UserName, name: FullName):
        this(UserId(UUID.randomUUID().toString()), userName, name)

    fun changeName(newName: FullName) = User(id, userName, newName)
    fun changeUserName(newUserName: UserName) = User(id, newUserName, name)

    override fun equals(other: Any?) = when {
        other is User -> id == other.id
        else -> false
    }

    override fun hashCode() = id.hashCode()
}

data class UserId(val value: String)

data class UserName(val value: String) {
    init {
        require(value.isNotBlank())
        require(value.length < 50)
    }
}

data class FullName(val firstName: String, val familyName: String) {}

interface UserRepository {
    fun find(id: UserId): User?
    fun find(name: UserName): User?
    fun findAll(): Iterable<User>
    fun save(user: User)
    fun remove(id: UserId)
}

fun UserId.isDuplicated(repository: UserRepository) = repository.find(this) != null
fun UserName.isDuplicated(repository: UserRepository) = repository.find(this) != null
