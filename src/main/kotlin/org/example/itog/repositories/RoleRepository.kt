package org.example.itog.repositories

import org.example.itog.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<UserRole, Long> {
    fun findByName(name:String) : UserRole?
}