package org.example.itog.repositories

import org.example.itog.model.Forum
import org.springframework.data.jpa.repository.JpaRepository

interface ForumRepository : JpaRepository<Forum, Long> {
}