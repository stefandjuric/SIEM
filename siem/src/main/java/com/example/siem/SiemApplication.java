package com.example.siem;

import com.example.siem.domain.*;
import com.example.siem.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;

@SpringBootApplication
public class SiemApplication implements CommandLineRunner{

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private AlarmRuleRepository alarmRuleRepository;

	@Autowired
	private AlarmRepository alarmRepository;

	@Autowired
	private AgentDataRepository agentDataRepository;

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
		alarmRuleRepository.deleteAll();
		alarmRepository.deleteAll();
		agentDataRepository.deleteAll();


		Log l = new Log("INFO", "Os is started", "2018-04-03 17:51:16","192.168.1.1",
				"host","facility","tag", "aaa");
		Log l1 = new Log("WARNING", "Os is stoped", "2018-04-03 17:51:16","192.1.1.23",
				"host","facility","tag","bbb");
		AlarmRule ar = new AlarmRule("ERROR" , null, null, null, null,
				null, null, 1, 0, true, false,
				false, false, false, false, false,
				false, false, false, false, false,
				false, false);

		logRepository.save(l);
		logRepository.save(l1);
		alarmRuleRepository.save(ar);


		System.out.println("Logs found with findAll():");
		System.out.println("-------------------------------");
		for (Log log : logRepository.findAll()) {
			System.out.println(log);
		}
	}

	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
									   MongoMappingContext context) {

		MappingMongoConverter converter =
				new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

		return mongoTemplate;

	}
}
