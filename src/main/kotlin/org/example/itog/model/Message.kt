package org.example.itog.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "messages")
class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var content: String = ""

    @Column(nullable = false)
    val sentAt: LocalDateTime = LocalDateTime.now()
}