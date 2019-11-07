package com.eva.blog.backend.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/comercios",
				"/api/comercios/{id}",
				"/api/comercios/{nombre}/busqueda",
				"/api/comercios/{mes}/mes",
				"/api/comercios/{mes}/comercio",
				"/api/comercios/meses",
				"/api/comentarios",
				"/api/comercios",
				"/api/comercios/maxVisitas",
				"/api/comercios/maxLikes",
				"/api/comercios/allActividad",
				"/api/clientes",
				"/api/descargas/img/{nombreFoto:.+}",
				"/api/descargasAdmin/**",
				"/api/fotoPortada/img/{fotoPortada}",
				"/api/comercios/date/comercio",
				"/api/comercios/{actividad}/busqueda",
				"/api/comentarios/{id}",
				"/api/comentarios/numero/{id_comercio}",
				"/api/clientes/email",
				"/api/usuarios/{id}",
				"/api/comentarios/comercio/{id_comercio}").permitAll()
		.antMatchers(HttpMethod.POST, "/api/comercios/{id}/visitas",
				"/api/sendmail/admin",
				"/api/clientes",
				"/api/sendmail",
				"/api/send/password",
				"/api/comercios/{id}/likes",
				"/api/comentarios/{id}").permitAll()	
		.antMatchers(HttpMethod.PUT, "/api/comercios/{id}/visitas",
				"/api/usuarios/{id}",
				"api/usuarios/password").permitAll()	
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
		
		
	}

		@Bean
		public FilterRegistrationBean<CorsFilter> corsFilter(){
			
			FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource() ));
			bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
			return bean;
		}
	
}
