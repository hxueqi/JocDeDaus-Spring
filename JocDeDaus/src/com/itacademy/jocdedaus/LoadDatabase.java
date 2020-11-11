package com.itacademy.jocdedaus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LoadDatabase {
	
	@Bean
	CommandLineRunner initDatabase(PlayerRepository playerRepository, GameRepository gameRepository) {
		return args ->{
			System.out.println("Preloading Data to memoryDatabase");
			Player p1 = new Player("Josep");
			playerRepository.save(p1);
			Player p2 = new Player("Joan");
			playerRepository.save(p2);
			//p2 = new Player("Joan");
			//playerRepository.save(p2);
			Player p3 = new Player("Anonym");
			playerRepository.save(p3);
			Player p4 = new Player("Anonym");
			playerRepository.save(p4);
			
			/*
			pictureRepository.save(new Picture("Jose", "Green land", "300", "10-02-2010", shop2));
			pictureRepository.save(new Picture("/", "Circle", "1000", "10-02-1996", shop1));
			pictureRepository.save(new Picture("Emily", "Mar", "1000", "10-02-1999", shop1));*/
			
			System.out.println("Data loaded");
		};
	}

}