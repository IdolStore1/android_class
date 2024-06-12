package com.example.idollapp.database

class UserRepository(private val database: UserDao) {

    suspend fun register(username: String, phone: String, password: String): Long {
        return database.insert(User(0, username, phone, password))
    }

    suspend fun login(username: String, password: String): User? {
        return database.findByUsernameAndPassword(username, password)
    }
}
