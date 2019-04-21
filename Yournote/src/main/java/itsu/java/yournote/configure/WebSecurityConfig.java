package itsu.java.yournote.model.configure;

import itsu.java.yournote.model.entity.LoginUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/img/**",
                "/css/**",
                "/scss/**",
                "/js/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/about.html").permitAll()
                .antMatchers("/register.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") //ログインページはコントローラを経由しないのでViewNameとの紐付けが必要
                .loginProcessingUrl("/login") //フォームのSubmitURL、このURLへリクエストが送られると認証処理が実行される
                //.usernameParameter("username") //リクエストパラメータのname属性を明示
                //.passwordParameter("password")
                .successForwardUrl("/mypage")
                .failureUrl("/login?error")
                .permitAll();
    }
}
