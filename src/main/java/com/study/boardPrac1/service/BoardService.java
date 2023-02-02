package com.study.boardPrac1.service;

import com.study.boardPrac1.entity.Board;
import com.study.boardPrac1.repository.BoardRepository;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글작성 처리, 첨부파일x
    public void boardWrite(Board board) {

        boardRepository.save(board);
    }

    //글작성 처리, 첨부파일o
    public void boardWrite(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);
        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
    }

    //게시글 리스트 처리
    @Transactional
    public List<Board> boardList() {

        return boardRepository.findAllDesc();
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }
}
