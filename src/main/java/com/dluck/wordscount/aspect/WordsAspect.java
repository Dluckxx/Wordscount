package com.dluck.wordscount.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WordsAspect {

	private final static Logger logger = LoggerFactory.getLogger(WordsAspect.class);

	@After("execution(public * com.dluck.wordscount.controller.WordController.upLoad(..))")
	public void doBeforeUpLoad() {
	}
}
