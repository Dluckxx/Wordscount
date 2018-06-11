package com.dluck.wordscount.repository;

import com.dluck.wordscount.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Integer> {

    //根据word查找
    List<Word> findWordByWord(String word);

}
