package com.study.boardPrac1.repository;

import com.study.boardPrac1.entity.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    //게시글 리스트 최신순으로 정렬
    @Query("select i from Board i order by i.id desc")
    List<Board> findAllDesc();

}
