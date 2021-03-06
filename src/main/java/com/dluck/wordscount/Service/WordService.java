package com.dluck.wordscount.Service;

import com.dluck.wordscount.domain.Word;
import com.dluck.wordscount.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	//清空数据库
	public boolean clearTable() {
		wordRepository.deleteAll();
		List<Word> list = wordRepository.findAll();
		return list.isEmpty();
	}

	//从数据库中删除一个word
	public boolean deleteFronDB(String word) {
		List<Word> list = wordRepository.findWordByWord(word);
		if (list.size() != 0) {
			wordRepository.delete(wordRepository.findWordByWord(word).get(0));
			return true;
		} else {
			return false;
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
//		List<Word> list = new ArrayList<>();
		//		infoIds.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
//		for (Map.Entry<String, Integer> infoId : infoIds) {
//			if (infoId.getValue() > count) {
//				Word word = new Word();
//				word.setWord(infoId.getKey());
//				word.setCount(infoId.getValue());
//
//				list.add(word);
//			}
//		}

		Map<String, Integer> result = new LinkedHashMap<>();
		map.entrySet().stream().filter(maps -> maps.getValue() > count).sorted(Map.Entry.<String, Integer>comparingByValue()
				.reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

//		List<Map.Entry<String, Integer>> maplist = new ArrayList<>(result.entrySet());
		return mapToWord(new ArrayList<>(result.entrySet()));
	}

	//排序Map
	List<Word> sortMap(Map<String, Integer> map) {
//		List<Word> list = new ArrayList<>();
//		List<Map.Entry<String, Integer>> infoIds = new ArrayList<>(map.entrySet());
//		infoIds.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
//		for (Map.Entry<String, Integer> infoId : infoIds) {
//			Word word = new Word();
//			word.setWord(infoId.getKey());
//			word.setCount(infoId.getValue());
//
//			list.add(word);
//		}
//		return list;
		Map<String, Integer> result = new LinkedHashMap<>();
		map.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByKey()
				.reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));

		return mapToWord(new ArrayList<>(result.entrySet()));
	}

	//list类型转换
	private List<Word> mapToWord(List<Map.Entry<String, Integer>> list) {
		List<Word> result = new ArrayList<>();
		for (Map.Entry<String, Integer> infoId : list) {
			Word word = new Word();
			word.setWord(infoId.getKey());
			word.setCount(infoId.getValue());

			result.add(word);
		}

		return result;
	}
}
