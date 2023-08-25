package com.example.board.service;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public List<Board> getList() {
        return (List<Board>) boardRepository.findAll();
    }

    public Board write(Board board) {
        return boardRepository.save(board);
    }

    public Board read(Long bno) {
        return boardRepository.findById(bno).orElse(null);
    }

    public Board modify(Board newBoard) {
        Board board = boardRepository.findById(newBoard.getBno()).orElse(null);

        if (board == null) {
            System.out.println("########################");
            return null;
        }
        else System.out.println("*********************");
        board.setTitle(newBoard.getTitle());
        board.setContent(newBoard.getContent());

        return boardRepository.save(board);
    }

    public void remove(Long bno) {
        Board board = boardRepository.findById(bno).orElse(null);
        if (board != null)
            boardRepository.deleteById(bno);

    }
}
