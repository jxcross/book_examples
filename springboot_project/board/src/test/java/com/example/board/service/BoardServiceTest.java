package com.example.board.service;

import com.example.board.model.Board;
import com.example.board.model.User;
import com.example.board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        for (Long i = 1L; i <= 10; i++) {
            Board b = new Board();
            b.setBno(i);
            b.setTitle("title-" + i);
            b.setContent("content-" + i);

            User user = new User();
            user.setId("user-" + (i%5));
            userRepository.save(user);

            b.setUser(user);
            b.setViewCnt(0L);
            b.setInDate(new Date());
            b.setUpDate(new Date());

            boardService.write(b);
        }
    }

    @Test
    public void getListTest() {
        List<Board> list = boardService.getList();
        System.out.println("list = " + list);
        assertEquals(list.size(), 10);
    }

    @Test
    public void writeAndReadTest() {
        User user = new User();
        user.setId("bbb");
        userRepository.save(user);

        Board b = new Board();
        b.setBno(11L);
        b.setTitle("new title");
        b.setContent("new content");
        b.setUser(user);
        b.setViewCnt(0L);
        b.setInDate(new Date());
        b.setUpDate(new Date());
        boardService.write(b);

        Board b2 = boardService.read(11L);
        assertTrue(b2 != null);
        assertEquals(b.getTitle(), b2.getTitle());
        assertEquals(b.getContent(), b2.getContent());

    }

    @Test
    public void modifyTest() {
        Board board = boardService.read(1L);
        board.setTitle("modified title");
        board.setContent("modified content");
        boardService.modify(board);

        Board board2 = boardService.read(1L);
        assertEquals(board.getTitle(), board2.getTitle());
        assertEquals(board.getContent(), board2.getContent());
    }

    @Test
    public void removeTest() {
        Long testBno = 5L;

        assertTrue(boardService.read(testBno) != null);

        boardService.remove(testBno);
        assertEquals(boardService.read(testBno), null);
    }
}