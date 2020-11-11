package com.itacademy.jocdedaus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Player {

	private @Id @GeneratedValue Long id;
	private String name;
	private String registerDate;
	
	public Player() {}
	
	public Player(String name) {
		this.name = name;
		setDateNowAsString();
	}
	
	public String setDateNowAsString () {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return this.registerDate = sdf.format(cal.getTime());
	}
}
