package BottomupDDD.domain.application

import kotlin.test.*
import BottomupDDD.test.helpers.*
import BottomupDDD.domain.user.*

class UserApplicationServiceTest {
    class RegisterUserTest {
        @Test fun testRegisterUser() {
            val service = noUsersRegistered()

            val user = someUser()
            service.registerUser(user)

            val findResult = service.getUserInfo(user.id)
            assertNotNull(findResult)
        }

        @Test fun testRegisterFailWhenUserNameIsDuplicated() {
            val (user, service) = someUserRegistered()

            val newUser = User(user.userName, someFullName())
            assertNotEquals(user.id, newUser.id)

            assertFailsWith(UserNameIsDuplicated::class) {
                service.registerUser(newUser)
            }

            val findResult = runCatching { service.getUserInfo(newUser.id) }.getOrNull()
            assertNull(findResult)
        }

        @Test fun testRegisterFailWhenUserIdIsDuplicated() {
            val (user, service) = someUserRegistered()

            val newUser = User(user.id, anotherUserName(), anotherFullName())

            assertFailsWith(UserIdIsDuplicated::class) {
                service.registerUser(newUser)
            }

            val findResult = service.getUserInfo(newUser.id)
            assertNotNull(findResult)
            assertNotEquals(newUser.userName, findResult.userName)
            assertNotEquals(newUser.name, findResult.name)
        }
    }

    class ChangeUserInfoTest {
        @Test fun testChangeUserInfo() {
            val (user, service) = someUserRegistered()

            val changed = user.changeName(anotherFullName())
            assertNotEquals(user.name, changed.name)

            service.changeUserInfo(changed)

            val findResult = service.getUserInfo(changed.id)
            assertNotNull(findResult)
            assertEquals(changed.name, findResult.name)
        }

        @Test fun testChangeFailWhenTargetUserDoesNotExist() {
            val service = noUsersRegistered()

            val user = someUser()
            assertFailsWith(UserIsNotFound::class) {
                service.changeUserInfo(user)
            }

            val findResult = runCatching { service.getUserInfo(user.id) }.getOrNull()
            assertNull(findResult)
        }

        @Test fun testChangeFailWhenChangedUserNameIsDuplicated() {
            val someUser = someUser()
            val anotherUser = anotherUser()
            val service = theseUsersRegistered(someUser, anotherUser)

            val changed = anotherUser.changeUserName(someUser.userName)
            assertNotEquals(anotherUser.userName, changed.userName)

            assertFailsWith(UserNameIsDuplicated::class) {
                service.changeUserInfo(changed)
            }

            val findResult = service.getUserInfo(changed.id)
            assertNotNull(findResult)
            assertEquals(anotherUser.userName, findResult.userName)
        }
    }

    class RemoveUserTest {
        @Test fun testRemoveUser() {
            val (user, service) = someUserRegistered()

            service.removeUser(user.id)

            val findResult = runCatching { service.getUserInfo(user.id) }.getOrNull()
            assertNull(findResult)
        }

        @Test fun testRemoveFailWhenTargetUserDoesNotExist() {
            val service = noUsersRegistered()
            val notRegistered = someUser()

            assertFailsWith(UserIsNotFound::class) {
                service.removeUser(notRegistered.id)
            }
        }
    }

    class GetUserInfoTest {
        @Test fun testGetUserInfo() {
            val (user, service) = someUserRegistered()

            val result = service.getUserInfo(user.id)
            assertEquals(user, result)
        }

        @Test fun testGetFailWhenTargetUserDoesNotExist() {
            val service = noUsersRegistered()
            val notRegistered = someUser()

            assertFailsWith(UserIsNotFound::class) {
                service.getUserInfo(notRegistered.id)
            }
        }
    }

    class GetUserListTest {
        @Test fun testGetUserInfo() {
            val someUser = someUser()
            val anotherUser = anotherUser()
            val service = theseUsersRegistered(someUser, anotherUser)

            val result = service.getUserList()
            assertEquals(setOf(someUser, anotherUser), result.toSet())
        }

        @Test fun testWhenNoUsersRegistered() {
            val service = noUsersRegistered()

            val result = service.getUserList()
            assertEquals(setOf(), result.toSet())
        }
    }
}
