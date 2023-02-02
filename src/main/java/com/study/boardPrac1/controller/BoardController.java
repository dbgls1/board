package com.study.boardPrac1.controller;

import com.study.boardPrac1.entity.Board;
import com.study.boardPrac1.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    //게시글 작성
    @GetMapping("/board/write")
    public String boardWriteForm() {

        return "boardwrite";
    }

    //게시글 작성 처리
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, MultipartFile file) throws Exception{

        if (file.isEmpty()) {

            boardService.boardWrite(board);

            return "redirect:/board/list";
        }else {

            boardService.boardWrite(board, file);

            return "redirect:/board/list";
        }
    }

    //게시글 리스트
    @GetMapping("/board/list")
    public String boardList(Model model) {

        model.addAttribute("list", boardService.boardList());
        return "boardlist";
    }

    //게시글 상세페이지
    @GetMapping("/board/view")
    public String boardview(Model model, Integer id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    //게시글 삭제
    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    //게시글 수정
    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id")Integer id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    //게시글 수정 처리
    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{

        if (file.isEmpty()) {

            Board boardTemp = boardService.boardView(id);
            boardTemp.setTitle(board.getTitle());
            boardTemp.setContent(board.getContent());
            boardService.boardWrite(boardTemp);

            return "redirect:/board/list";
        }else {

            Board boardTemp = boardService.boardView(id);
            boardTemp.setTitle(board.getTitle());
            boardTemp.setContent(board.getContent());
            boardTemp.setFilepath(board.getFilepath());
            boardTemp.setFilename(board.getFilename());
            boardService.boardWrite(boardTemp, file);

            return "redirect:/board/list";
        }
    }
}
