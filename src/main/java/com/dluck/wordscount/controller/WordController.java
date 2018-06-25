package com.dluck.wordscount.controller;

import com.dluck.wordscount.Service.FileService;
import com.dluck.wordscount.Service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class WordController {

	@Autowired
	private WordService wordService;

	@Autowired
	private FileService fileService;

	/**
	 * 添加一个词，可不断重复添加，前往addwords必须先前往add
	 *
	 * @return 返回到addwords页面
	 */
	@GetMapping(value = "/add")
	public String add() {
		return "addwords";
	}

	/**
	 * 前往add来执行次方法，直接访问addwords报错
	 *
	 * @param word 单词
	 * @return 返回到addwords页面
	 */
	@PostMapping(value = "/addwords")
	public String addWords(@RequestParam("word") String word) {
		wordService.addWordToDB(word);
		return "addwords";
	}

	/**
	 * 访问upload来访问上传文件页面。
	 *
	 * @return 跳转到upload页面
	 */
	@GetMapping(value = "/")
	public String upLoad() {
		return "upload";
	}

	@GetMapping("/clear")
	public String clearTable() {
		if (wordService.clearTable()) {
			return "upload";
		}else {
			return "error";
		}
	}

	/**
	 * 显示词频统计结果，上传文件后，调用此方法，返回result页面。
	 *
	 * @param file 传入一个文件
	 * @param map  map
	 * @return result页面
	 */
	@PostMapping(value = "/resultbydb")
	public String listWordByDB(@RequestParam(value = "file", required = false) MultipartFile file, ModelMap map) {
		try {
			if (file != null) {
				fileService.readToDB(fileService.getBufferedReader(file.getInputStream()));
			}
		} catch (IOException e) {
			e.printStackTrace();
			map.addAttribute("msg", "文件读取错误");
			return "err";
		}
		map.addAttribute("list", wordService.listAll());
		return "result";
	}

	@PostMapping(value = "/resultbymap")
	public String listWordByList(@RequestParam(value = "file", required = false) MultipartFile file,
	                             @RequestParam(value = "count", required = false) Integer count, ModelMap map) {
		try {
			if (file != null) {
				map.addAttribute("list", fileService.readByHashMap(fileService.getBufferedReader(file.getInputStream()), count));
			}
		} catch (IOException e) {
			e.printStackTrace();
			map.addAttribute("msg", "文件读取错误");
			return "error";
		}
		return "result";
	}
}