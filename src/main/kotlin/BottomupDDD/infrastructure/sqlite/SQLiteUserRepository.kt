package BottomupDDD.infrastructure.sqlite

import BottomupDDD.domain.user.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import java.sql.Connection

class SQLiteUserRepository(private val connect: () -> Database): UserRepository {
    private val db by lazy(connect)

    object Users: Table() {
        val id = varchar("id", 50).primaryKey()
        val userName = varchar("username", 50)
        val firstName = text("firstname")
        val familyName = text("familyName")
    }

    fun ResultRow.toUser(): User {
        val id = UserId(this[Users.id])
        val userName = UserName(this[Users.userName])
        val name = FullName(this[Users.firstName], this[Users.familyName])
        return User(id, userName, name)
    }

    private fun <T> _userTransaction(block: () -> T) = transaction(db) {
        SchemaUtils.create(Users)
        block()
    }

    private fun _find(where: SqlExpressionBuilder.() -> Op<Boolean>): User? {
        return _userTransaction {
            Users.select(where).firstOrNull()?.toUser()
        }
    }

    override fun find(id: UserId) = _find { Users.id.eq(id.value) }
    override fun find(name: UserName) = _find { Users.userName.eq(name.value) }
    override fun findAll() = _userTransaction { Users.selectAll().map { it.toUser() } }

    override fun save(user: User) {
        _userTransaction {
            val notExist = Users.select { Users.id.eq(user.id.value) }.empty()

            if (notExist) {
                Users.insert {
                    it[id] = user.id.value
                    it[userName] = user.userName.value
                    it[firstName] = user.name.firstName
                    it[familyName] = user.name.familyName
                }
            } else {
                Users.update({ Users.id.eq(user.id.value) }) {
                    it[userName] = user.userName.value
                    it[firstName] = user.name.firstName
                    it[familyName] = user.name.familyName
                }
            }

            Unit
        }
    }

    override fun remove(id: UserId) {
        _userTransaction {
            Users.deleteWhere { Users.id.eq(id.value) }
        }
    }
}
