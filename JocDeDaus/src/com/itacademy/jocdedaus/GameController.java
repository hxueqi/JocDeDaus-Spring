package com.itacademy.jocdedaus;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
	
	public static GameRepository repository;
	public static GameController gameController;

	GameController(GameRepository repo) {
	    repository = repo;
	    gameController = this;
	}
	
	@PostMapping("/players/{id}/games")
	Game newGame(@RequestBody Game newGame, @PathVariable Long id) {
		Optional<Player> optional = PlayerController.repository.findById(id);
		newGame.setPlayer(optional.get());
	    return repository.save(newGame);
	}
	
	@DeleteMapping("/players/{id}/games")
	void deleteGame(@PathVariable Long id) {
		repository.deleteAll(this.getAllGamesByPlayerId(id));
		//repository.deleteAllUserGames(id);
		//int deleteCount= repository.createQuery("DELETE FROM Account where id =:clientId").setParameter("clientId", clientId).executeUpdate();
	}

	@GetMapping("/players/{id}/games")
	List<Game> getAllGamesByPlayerId(@PathVariable Long id) {
	    return repository.getGamesByPlayerId(id);
	}
}
