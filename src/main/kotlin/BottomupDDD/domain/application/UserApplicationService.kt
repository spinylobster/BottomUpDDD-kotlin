package BottomupDDD.domain.application

import BottomupDDD.domain.user.*

class UserApplicationService(val repository: UserRepository) {
    fun registerUser(user: User) {
        if (user.id.isDuplicated(repository)) {
            throw UserIdIsDuplicated()
        }
        if (user.userName.isDuplicated(repository)) {
            throw UserNameIsDuplicated()
        }

        repository.save(user)
    }

    fun changeUserInfo(user: User) {
        val beforeChange = repository.find(user.id) ?: throw UserIsNotFound()

        val userNameWillChange = beforeChange.userName != user.userName
        if (userNameWillChange && user.userName.isDuplicated(repository)) {
            throw UserNameIsDuplicated()
        }

        repository.save(user)
    }

    fun removeUser(id: UserId) {
        repository.find(id) ?: throw UserIsNotFound()

        repository.remove(id)
    }

    fun getUserInfo(id: UserId): User {
        val target = repository.find(id) ?: throw UserIsNotFound()

        return target
    }

    fun getUserList(): Iterable<User> = repository.findAll()
}

class UserIdIsDuplicated : Exception("User id is duplicated.")
class UserNameIsDuplicated : Exception("User name is duplicated.")
class UserIsNotFound : Exception("User is not found.")
