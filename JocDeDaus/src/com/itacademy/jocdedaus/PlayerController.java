package com.itacademy.jocdedaus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {
	public static PlayerRepository repository;

	PlayerController(PlayerRepository repo) {
	    repository = repo;
	}

	@PostMapping("/players")
	Player newPlayer(@RequestBody Player newPlayer) {
		if (newPlayer.getName()==null) newPlayer.setName("Anonym");
		newPlayer.setDateNowAsString();
		if (newPlayer.getName().equalsIgnoreCase("Anonym")) return repository.save(newPlayer);
		if (repository.existsPlayer(newPlayer.getName()).size()>0) throw new RuntimeException("Already exists one player with name "+newPlayer.getName());
	    return repository.save(newPlayer);
	}

	@PutMapping("/players/{id}")
	Player replacePlayer(@RequestBody Player newPlayer, @PathVariable Long id) {
		if (newPlayer.getName()==null) newPlayer.setName("Anonym");
		if (!newPlayer.getName().equalsIgnoreCase("Anonym")) {
			if (repository.existsPlayer(newPlayer.getName()).size()>0) throw new RuntimeException("Already exists one player with name "+newPlayer.getName()+". It could be yourself");
		}
		
	    return repository.findById(id)
	      .map(player -> {
	    	  player.setName(newPlayer.getName());
	        return repository.save(player);
	      })
	      .orElseGet(() -> {
	    	newPlayer.setDateNowAsString();
	        newPlayer.setId(id);
	        return repository.save(newPlayer);
	      });
	}
	
	@GetMapping("/players")
	  List<Player> all() {
	    return repository.findAll();
	  }
	
	@GetMapping("/players/ranking")
	String getRanking() {
	    List<Player> players = all();
	    if (players.size()==0) return "No player in the system yet";
	    int totalGames = 0, totalWinners = 0;
	    for (Player p : players) {
	    	List<Game> games = GameController.gameController.getAllGamesByPlayerId(p.getId());
	    	int winnerGames = getNumberOfWinnerGames(games);
	    	totalGames+=games.size();
	    	totalWinners+=winnerGames;
	    }
	    if (totalGames==0) return "No games registered yet";
	    return ((totalWinners*100.0)/totalGames)+"%";
	}
	
	private int getNumberOfWinnerGames (List<Game> games) {
		int result = 0;
		for (Game g : games) {
			if (g.isWinner()) result++;
		}
		return result;
	}
	
	private List<Player> getWinnerOrLoser (boolean findWinner) {
		List<Player> players = all();
	    if (players.size()==0) return null;
	    List<Player> result = new ArrayList<>();
	    double bestRanking = Double.MIN_VALUE, worstRanking = Double.MAX_VALUE;
	    for (Player p : players) {
	    	List<Game> games = GameController.gameController.getAllGamesByPlayerId(p.getId());
	    	int winnerGames = getNumberOfWinnerGames(games);
	    	if (games.size()==0) continue;
	    	double ranking = ((double)winnerGames)/((double)games.size());
	    	if (findWinner) {
	    		if (ranking>bestRanking) {
	    			bestRanking = ranking;
	    			result.clear();
	    			result.add(p);
	    		}
	    		else if (ranking==bestRanking) {
	    			result.add(p);
	    		}
	    	}
	    	else {
	    		if (ranking<worstRanking) {
	    			worstRanking = ranking;
	    			result.clear();
	    			result.add(p);
	    		}
	    		else if (ranking==worstRanking) {
	    			result.add(p);
	    		}
	    	}
	    }
	    if (bestRanking==Double.MIN_VALUE && worstRanking==Double.MAX_VALUE) return null;
	    return result;
	}
	
	@GetMapping("/players/ranking/winner")
	  List<Player> findWinner() {
	    return getWinnerOrLoser(true);
	  }
	@GetMapping("/players/ranking/loser")
	  List<Player> findLoser() {
		return getWinnerOrLoser(false);
	  }
}