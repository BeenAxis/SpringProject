package org.example.itog

import org.example.itog.model.User
import org.example.itog.repositories.RoleRepository
import org.example.itog.repositories.UserRepository
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Service

@Service
class PersonDetailsService(
    private val userRepository: UserRepository,
    private val roleRepo: RoleRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        if(username == "root")
            return User(
                "root",
                BCryptPasswordEncoder().encode("root"),
                roleRepo.findByName("Root")!!
            )
        val user = userRepository.getByLogin(username)
        if(user!=null){
            return User(
                user.login,
                user.pass,
                user.role!!
            )
        }else{
            return User(
                "guest",
                BCryptPasswordEncoder().encode("guest"),
                roleRepo.findByName("Guest")!!
            )
        }
    }
}


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val userDetailsService: PersonDetailsService,
) {
    @Bean
    protected fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    fun configure(http: HttpSecurity): DefaultSecurityFilterChain? {
        http
            .authorizeHttpRequests {
            it.requestMatchers("/register", "/login", "/logout").permitAll()
                .requestMatchers(
                    "/",
                    "/index",
                    "/message",
                    "/forum",
                    "/register",
                ).permitAll()
                .requestMatchers(
                    "/forum/**",
                    "/forum/post/**",
                    "/message/**",
                    "/message/send",
                ).hasAnyAuthority("User", "Admin")
                .requestMatchers(
                    "/role",
                    "/role/**",
                    "/cat/**",
                    "/tag/**").hasAnyAuthority("Admin")
                .requestMatchers("/role-edit", "/change-user-role").hasAnyAuthority("Admin")
                .anyRequest().hasAuthority("Admin")
        }.formLogin(withDefaults())
            .logout {
                it.logoutUrl("/logout") // URL, на который отправляется запрос для выхода
                    .logoutSuccessUrl("/login?logout") // URL, на который перенаправляется пользователь после выхода
                    .permitAll()
            }
        return http.build()
    }
}

