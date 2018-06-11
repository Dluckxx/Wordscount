package com.dluck.wordscount.Service;

import com.dluck.wordscount.domain.Word;
import com.dluck.wordscount.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WordService {

	@Autowired
	private WordRepository wordRepository;

	//返回全部信息
	public List<Word> listAll() {
		return wordRepository.findAll();
	}

	//Word添加/增加词频到数据库
	public void addWordToDB(String word) {
		List<Word> list = wordRepository.findWordByWord(word);
		if (list.size() == 0) {
			Word w = new Word(word);
			wordRepository.save(w);
		} else {
			list.get(0).add();
			wordRepository.save(list.get(0));
		}
	}

	//Word添加/增加词频到HashMap
	void addWordToMap(String word, Map<String, Integer> hashmap) {
		if (hashmap.containsKey(word))
			hashmap.put(word, hashmap.get(word) + 1);
		else
			hashmap.put(word, 1);
	}

	//排序Map只列出count个以上的数据
	List<Word> sortMap(Map<String, Integer> map, Integer count) {
		List<Word> list = new ArrayList<>();
		List<Map.Entry<String, Integer>> infoIds = new ArrayList<>(map.entrySet());
		infoIds.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
		for (Map.Entry<String, Integer> infoId : infoIds) {
			if (infoId.getValue() > count) {
				Word word = new Word();
				word.setWord(infoId.getKey());
				word.setCount(infoId.getValue());

				list.add(word);
			}
		}
		return list;
	}

	//排序Map
	List<Word> sortMap(Map<String, Integer> map) {
		List<Word> list = new ArrayList<>();
		List<Map.Entry<String, Integer>> infoIds = new ArrayList<>(map.entrySet());
		infoIds.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
		for (Map.Entry<String, Integer> infoId : infoIds) {
			Word word = new Word();
			word.setWord(infoId.getKey());
			word.setCount(infoId.getValue());

			list.add(word);
		}
		return list;
	}
}
