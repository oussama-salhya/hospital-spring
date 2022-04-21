package ma.emsi.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
// pour dire que c'est une classe de configuration
//chaque classe qui utilise l'annotation config va etre instancier du 1er lieu
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        //la methode configure avec AuthenticationManagerBuilder va servir pour preciser quel strategie
        //que vous vouler utiliser pourque spring security va chercher l'utilisateur
        auth.inMemoryAuthentication().withUser("user1").password("{noop}1234").roles("USER") // spring va chercher parmis ces users
                .and().
                withUser("user2").password("1234").roles("USER")
                .and().
                withUser("admin").password("1234").roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // pour specifier les droits d'access
        http.formLogin(); // vous demander a spring security je veux utliser un formulaire d'authentification
        // pour utliser votre formulaire on ajout:
        //http.formLogin().loginPage("/login");
        http.authorizeRequests().anyRequest().authenticated();
        //qlq soit les ressources utilisé dans l'app necessite une auth


    }
    @Bean
    BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }
}
