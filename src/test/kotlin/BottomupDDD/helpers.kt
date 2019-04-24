package BottomupDDD.test.helpers

import BottomupDDD.domain.user.*
import BottomupDDD.domain.application.*
import BottomupDDD.infrastructure.inMemory.*

// Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
fun someUserName() = UserName("Lorem")
fun someFullName() = FullName("ipsum", "dolor")
fun someUser() = User(someUserName(), someFullName())
fun anotherUserName() = UserName("sit")
fun anotherFullName() = FullName("amet", "consectetur")
fun anotherUser() = User(anotherUserName(), anotherFullName())

fun noUsersRegistered() = UserApplicationService(InMemoryUserRepository())
fun someUserRegistered(): Pair<User, UserApplicationService> {
    val user = someUser()
    val repository = InMemoryUserRepository(user)
    val service = UserApplicationService(repository)
    return Pair(user, service)
}
fun theseUsersRegistered(vararg users: User) = UserApplicationService(InMemoryUserRepository(*users))
