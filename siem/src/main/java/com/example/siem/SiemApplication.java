package com.example.siem;

import com.example.siem.domain.*;
import com.example.siem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class SiemApplication implements CommandLineRunner{

	@Autowired
	private LogRepository logRepository;

	public static void main(String[] args) {
		SpringApplication.run(SiemApplication.class, args);
	}


	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedHeaders("content-type", "x-auth-token");
			}
		};

	}

	@Override
	public void run(String... strings) throws Exception {
		logRepository.deleteAll();
		//adminRepository.deleteAll();
		//authorityRepository.deleteAll();
		//userRepository.deleteAll();
		//userAuthorityRepository.deleteAll();


		Log l = new Log("INFO", "Os is started", null);
		Log l1 = new Log("WARNING", "Os is stoped", null);

		//Admin admin = new Admin(new User("admin", "admin"));

		//Authority authority = new Authority("ROLE_ADMIN");

		//User user = new User("a","$2a$10$S3rxpwjnJUrmgMrnMCJo8eIRCFvCcmzuPi5Y3Okz67i/2sj6xMfau");


		logRepository.save(l);
		logRepository.save(l1);
		//adminRepository.save(admin);
		//authority = authorityRepository.save(authority);
		//user = userRepository.save(user);
		//UserAuthority userAuthority = new UserAuthority(user, authority);

		//userAuthority = userAuthorityRepository.save(userAuthority);
		//System.out.println("111111111111");
		//user.addUserAuthority(userAuthority);
		//System.out.println("222222222222");
		//System.out.println(user.getUserAuthorities().size());
		//userRepository.save(user);
		//System.out.println("333333333333");

		System.out.println("Logs found with findAll():");
		System.out.println("-------------------------------");
		for (Log log : logRepository.findAll()) {
			System.out.println(log);
		}
	}
}
