package org.example.itog.model
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "tags")
class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var name: String = ""

    @ManyToMany(mappedBy = "tags", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val posts: Set<Post> = mutableSetOf()
}