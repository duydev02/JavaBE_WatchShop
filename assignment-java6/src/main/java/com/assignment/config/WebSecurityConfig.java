package com.assignment.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.assignment.jwt.JwtAuthenticationFilter;
import com.assignment.service.impl.CustomUserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserServiceImpl customUserService;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private EncoderConfig encoderConfig;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserService).passwordEncoder(encoderConfig.passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		/*
		  	Su dung doan code nay de chan cac request tuong ung voi role ADMIN
		  	Khi xay dung chuc nang cho phia admin thi dung doan code nay de khong cho
		     cac user thong thuong duoc goi api admin va truy cap trang admin
		 
		 	http.authorizeRequests().antMatchers("/admin/**").hasAuthority(RoleConst.ROLE_ADMIN);
		 */
		http.authorizeRequests().antMatchers("/admin/**").hasAuthority("admin");
		
		http.logout(logout -> logout                                                
	            .logoutUrl("/logout")                                            
	            .logoutSuccessUrl("/index")
	            .deleteCookies("JSESSIONID")
	            .invalidateHttpSession(true));
		
		http.cors()
				.and().authorizeRequests()
				.antMatchers("/api/login", "/", "/index", "/login", "/logout", "/register",
						"/cart", "/shop", "/about", "/blog", "/blog-details", "/contact",
						"/product-details/**", "/active-account", "/forgot-password", "/reset-password")
				.permitAll() // Cho phep tat ca truy cap link nay
				.anyRequest().authenticated(); // Cac link con lai thi phai xac thuc
		
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/user/assets/**");
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}
}
