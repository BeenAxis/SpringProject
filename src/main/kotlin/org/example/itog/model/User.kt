package org.example.itog.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    constructor()

    constructor(login:String, password:String, role:UserRole){
        this.login = login
        this.pass = password
        this.role = role
    }


    @Column(nullable = false, unique = true)
    var login: String = ""

    @Column(nullable = false)
    var pass: String = ""

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val posts: MutableList<Post> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val comments: MutableList<Comment> = mutableListOf()

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var role: UserRole? = null

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val a = role?.name ?: "GUEST"
        println("A: $a")
        return mutableListOf(SimpleGrantedAuthority(a))
    }

    override fun getPassword(): String {
        return pass
    }

    override fun getUsername(): String {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}