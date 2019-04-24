package BottomupDDD.domain.user

import kotlin.test.*
import BottomupDDD.test.helpers.*

class UserTest {
    @Test fun testFullNameShouldBeEquatable() {
        val name1 = FullName("tarou", "tanaka")
        val name2 = FullName("john", "smith")
        assertNotEquals(name1, name2)
    }

    @Test fun testUserNameShouldNotBeBlank() {
        val blankName = " 　\t\n"
        assertFails { UserName(blankName) }
    }

    @Test fun testUserNameLengthShouldBeLessThan50() {
        val FiftyLengthName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        assertFails { UserName(FiftyLengthName) }
    }

    @Test fun testUserShouldBeEquatable() {
        val name = FullName("taro", "tanaka")
        val user = User(someUserName(), name)
        val newName = FullName("taro", "sato")
        val user_ = user.changeName(newName)
        assertEquals(user, user_)
    }
}
