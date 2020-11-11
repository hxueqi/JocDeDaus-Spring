package com.itacademy.jocdedaus;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Game {
	private static final int N_DICES = 2;
	private static final Random rd = new Random();
	
	private @Id @GeneratedValue Long id;
	private String dices = "";
	private int sum = 0;
	private boolean winner;
	@ManyToOne
	private Player player;
	
	public Game(Player player) {	
		init();
	}
	
	public Game () {
		init();
	}
	
	private void init () {
		playDices();
		winner = checkGame();
	}
	
	private void playDices () {
		for (int i=0; i<N_DICES; i++) {
			if (dices.length()>0) dices+=", ";
			int x = rd.nextInt(6)+1;
			dices+=x;
			sum+=x;
		}
	}
	
	private boolean checkGame () {
		return sum==7;
	}
}
