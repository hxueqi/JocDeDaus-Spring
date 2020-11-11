package com.itacademy.jocdedaus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;


public  interface PlayerRepository extends JpaRepository<Player, Long>{

	@Query("select p from Player p where p.name = ?1")
	List<Player> existsPlayer(@PathVariable String name);
}
