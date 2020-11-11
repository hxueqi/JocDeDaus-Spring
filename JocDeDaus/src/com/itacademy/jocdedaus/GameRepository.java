package com.itacademy.jocdedaus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;


public interface GameRepository extends JpaRepository<Game, Long>{
	
	//@Transactional
	@Modifying
	@Query("delete from Game g where g.player.id=?1")
	//List<int> deleteFruits(@Param("name") String name, @Param("color") String color);
	void deleteAllUserGames(Long id);
	
	@Query("select g from Game g where g.player.id = ?1")
	List<Game> getGamesByPlayerId(@PathVariable Long id);
}
