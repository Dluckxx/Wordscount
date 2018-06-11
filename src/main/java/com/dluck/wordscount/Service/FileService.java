package com.dluck.wordscount.Service;

import com.dluck.wordscount.domain.Word;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

	@Autowired
	private WordService wordService;

	/**
	 * 获取BufferedReader
	 *
	 * @param inputStream 字节输入流
	 * @return 字符输入流
	 */
	public BufferedReader getBufferedReader(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	/**
	 * 读取文件数据到数据库
	 *
	 * @param bufferedReader 字符输入流
	 * @throws IOException 读取失败时抛出
	 */
	public void readToDB(BufferedReader bufferedReader) throws IOException {

		JiebaSegmenter segmenter = new JiebaSegmenter();
		String str;

		while ((str = bufferedReader.readLine()) != null) {
			List<SegToken> tokenList = segmenter.process(str, JiebaSegmenter.SegMode.INDEX);
			for (SegToken word : tokenList) {
				if (word.word.length() > 1) {
					wordService.addWordToDB(word.word);
				}
			}
		}
	}

	/**
	 * 读取文件到Map
	 *
	 * @param bufferedReader 字符输入流
	 * @return 从输入流读取的词频Map
	 * @throws IOException 读取失败时抛出
	 */
	public List<Word> readByHashMap(BufferedReader bufferedReader, Integer count) throws IOException {
		JiebaSegmenter segmenter = new JiebaSegmenter();
		Map<String, Integer> wordsMap = new HashMap<>();
		read(bufferedReader, segmenter, wordsMap);
		return wordService.sortMap(wordsMap, count);
	}

	/**
	 * 读取文件到Map
	 *
	 * @param bufferedReader 字符输入流
	 * @return 从输入流读取的词频Map
	 * @throws IOException 读取失败时抛出
	 */
	public List<Word> readByHashMap(BufferedReader bufferedReader) throws IOException {
		JiebaSegmenter segmenter = new JiebaSegmenter();
		Map<String, Integer> wordsMap = new HashMap<>();
		read(bufferedReader, segmenter, wordsMap);
		return wordService.sortMap(wordsMap);
	}

	private void read(BufferedReader bufferedReader, JiebaSegmenter segmenter, Map<String, Integer> wordsMap) throws IOException {
		String str;
		while ((str = bufferedReader.readLine()) != null) {
			List<SegToken> tokenList = segmenter.process(str, JiebaSegmenter.SegMode.INDEX);
			for (SegToken word : tokenList) {
				if (word.word.length() > 1) {
					wordService.addWordToMap(word.word, wordsMap);
				}
			}
		}
	}
}
