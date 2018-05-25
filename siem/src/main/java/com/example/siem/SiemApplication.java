package com.example.siem;

import com.example.siem.domain.Log;
import com.example.siem.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class SiemApplication implements CommandLineRunner{

	@Autowired
	private LogRepository logRepository;

	public static void main(String[] args) {
		SpringApplication.run(SiemApplication.class, args);
	}


	@Override
	public void run(String... strings) throws Exception {

		logRepository.deleteAll();

		Log l = new Log("INFO", "Os is started", null);

		logRepository.save(l);

		System.out.println("Logs found with findAll():");
		System.out.println("-------------------------------");
		for (Log log : logRepository.findAll()) {
			System.out.println(log);
		}
	}
}
