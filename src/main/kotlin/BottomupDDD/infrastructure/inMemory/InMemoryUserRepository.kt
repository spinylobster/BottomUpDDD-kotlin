package BottomupDDD.infrastructure.inMemory

import BottomupDDD.domain.user.*

class InMemoryUserRepository(private val data: MutableMap<UserId, User>): UserRepository {
    constructor(): this(mutableMapOf())
    constructor(vararg users: User): this(users.map { Pair(it.id, it) }.toMap().toMutableMap())

    override fun find(id: UserId) = data[id]
    override fun find(name: UserName) = data.values.find { it.userName == name }
    override fun findAll() = data.values

    override fun save(user: User) { data[user.id] = user }
    override fun remove(id: UserId) { data.remove(id) }
}
