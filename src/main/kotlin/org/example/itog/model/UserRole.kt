package org.example.itog.model

import jakarta.persistence.*

@Entity
@Table(name = "roles")
class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var name: String = ""
}