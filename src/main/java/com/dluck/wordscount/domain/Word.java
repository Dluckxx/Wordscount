package com.dluck.wordscount.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Word {

	@Id
	@GeneratedValue
	private Integer id;
	private String word;
	private Integer count = 0;

	public Word() {
	}

	public Word(String word) {
		this.word = word;
		count++;
	}

	public void add() {
		count++;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
