package org.example.itog.model

import jakarta.persistence.*

@Entity
@Table(name = "forums")
class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var name: String = ""

    @Column(nullable = false)
    var description: String = ""

    @OneToMany(mappedBy = "forum", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val posts: List<Post> = mutableListOf()
}