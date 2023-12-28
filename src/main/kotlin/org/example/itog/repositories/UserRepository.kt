package org.example.itog.repositories

import org.example.itog.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun getByLogin(login: String): User?
}