package com.example.board.repository;

import com.example.board.model.Board;
import com.example.board.model.QBoard;
import com.example.board.model.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static com.example.board.model.QBoard.board;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public EntityManager em;

/*
    @Test
    @Order(2)
    public void selectTest() {
        Board board = boardRepository.findById(1L).orElse(null);
        assertTrue(board != null);
    }

    @Test
    @Order(1)
    public void insertTest() {
        Board board = new Board();
        board.setBno(100L);
        board.setTitle("title1");
        board.setContent("content1");
        board.setWriter("writer1");
        board.setViewCnt(0L);
        board.setInDate(new Date());
        board.setUpDate(new Date());

        boardRepository.save(board);
    }

    @Test
    @Order(3)
    public void updateTest() {
        Board board = boardRepository.findById(1L).orElse(null);
        assertTrue(board != null);

        board.setTitle("modified title");
        boardRepository.save(board);

        Board board2 = boardRepository.findById(1L).orElse(new Board());
        assertTrue(board.getTitle().equals(board2.getTitle()));
    }

    @Test
    @Order(4)
    public void deleteTest() {
        boardRepository.deleteById(1L);

        Board board = boardRepository.findById(1L).orElse(null);
        assertTrue(board == null);
    }
*/
//    @BeforeEach
//    public void testData() {
//        for (int i = 0; i < 100; i++) {
//            Board board = new Board();
//            //board.setBno((long) i);
//            board.setTitle("title-" + i);
//            board.setContent("content-" + i);
//            board.setViewCnt((long)(Math.random()*100));
//            board.setWriter("writer-" + (i%5));
//            board.setInDate(new Date());
//            board.setUpDate(new Date());
//
//            boardRepository.save(board);
//        }
//    }

    @Test
    public void countTest() {
        assertTrue(boardRepository.countAllByWriter("writer-1") == 20);
    }

    @Test
    public void findTest() {
        List<Board> list = boardRepository.findByWriter("writer-1");
        assertTrue(list.size() == 20);
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("delete test...")
    public void deleteTest() {
        assertTrue(boardRepository.deleteByWriter("writer-1") == 20);
    }

    @Test
    @DisplayName("createQuery test...")
    public void createQueryTest() {
        String query = "SELECT b FROM Board b";
        TypedQuery<Board> tQuery = em.createQuery(query, Board.class);
        List<Board> list = tQuery.getResultList();
        System.out.println("list size=" + list.size());

        list.forEach(System.out::println);
        assertTrue(list.size() == 100);
    }

    @Test
    @DisplayName("@Query, JPQL test")
    public void queryAnnoTest() {
        List<Board> list = boardRepository.findAllBoard();
        assertTrue(list.size() == 100);
    }

    @Test
    @DisplayName("@Query, Object[]")
    public void queryAnnoTest2() {
        List<Object[]> list = boardRepository.findTitleAndWriter();
        list.stream()
                .map(arr-> Arrays.toString(arr))
                .forEach(System.out::println);
        assertTrue(list.size() == 100);
    }

    /*
    JPA Criteria API

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Board> cq = cb.createQuery(Board.class);
        Root<Board> board = cq.from(Board.class);
        cq.select(board).where(cb.equal(board.get("title"), "title-1"));

        TypedQuery<Board> tq = em.createQuery(cq);
        List<Board> list = tq.getResultList();
     */

    // QBoard, QUser 생성을 위해,
    // com.querydsl.apt.jpa.JPAAnnotationProcessor plugin 설치 및 maven compile 실행 -> maven project reload
    // import static com.example.board.model.QBoard.board; 추가
    /*
        Querydsl

        QBoard board = QBoard.board;
        JPAQueryFactory qf = new JPAQueryFactory(em);

        List<Board> list = qf.selectFrom(board)
                            .where(board.title.eq("title-1"))
                            .fetch();

        // JPAQuery<Board> query = qf.selectFrom(board).where(board.title.eq("title-1"));
        // List<Board> list = query.fetch();
     */

    /* Boolean builder

        BooleanBuilder builder = new BooleanBuilder();
        builder.add(board.title.like(keyword).or(board.content.like(keyword)));

        JPAQuery<Board> query = qf.selectFrom(board)
                                    .where(builder)
                                    .orderBy(board.upDate.desc());
     */

    @Test
    @DisplayName("querydsl test-1")
    public void querydslTest1() {
        // QBoard board = QBoard.board;
        // import static com.example.board.model.QBoard.board;
        JPAQueryFactory qf = new JPAQueryFactory(em);
        JPAQuery<Board> query = qf.selectFrom(board)
                .where(board.title.eq("title-1"));
        List<Board> list = query.fetch();
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("querydsl test...")
    public void querydslTest2() {
        //QBoard board = QBoard.board;
        //import static com.example.board.model.QBoard.board;
        JPAQueryFactory qf = new JPAQueryFactory(em);

        JPAQuery<Tuple> query = qf.select(board.writer, board.viewCnt.sum())
                .from(board)
                .where(board.title.notLike("title-1%"))
                .where(board.writer.eq("writer-1"))
                .where(board.content.contains("content"))
                .where(board.content.isNotNull())
                .groupBy(board.writer)
                .having(board.viewCnt.sum().gt(50))
                .orderBy(board.writer.asc())
                .orderBy(board.viewCnt.sum().desc());

        List<Tuple> list = query.fetch();
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("querydsl - 동적 쿼리 ")
    public void querydslTest3() {
        String searchBy = "TC";
        String keyword = "1";
        keyword = "%" + keyword + "%";

        BooleanBuilder builder = new BooleanBuilder();

        if (searchBy.equalsIgnoreCase("T"))
            builder.and(board.title.like(keyword));
        else if (searchBy.equalsIgnoreCase("C"))
            builder.and(board.content.like(keyword));
        else if (searchBy.equalsIgnoreCase("TC"))
            builder.and(board.title.like(keyword).or(board.content.like(keyword)));

        JPAQueryFactory qf = new JPAQueryFactory(em);
        JPAQuery query = qf.selectFrom(board)
                .where(builder)
                .orderBy(board.upDate.desc());

        List<Board> list = query.fetch();
        list.forEach(System.out::println);

    }

    @Test
    public void manyToOneTest() {
        User user = new User();
        user.setId("aaa");
        user.setPassword("1234");
        user.setName("LEE");
        user.setEmail("aaa@aaa.com");
        user.setInDate(new Date());
        user.setUpDate(new Date());
        userRepository.save(user);

        Board b1 = new Board();
        b1.setBno(1L);
        b1.setTitle("title1");
        b1.setContent("content1");
        b1.setUser(user);
        b1.setViewCnt(0L);
        b1.setInDate(new Date());
        b1.setUpDate(new Date());
        boardRepository.save(b1);

        Board b2 = new Board();
        b2.setBno(2L);
        b2.setTitle("title2");
        b2.setContent("content2");
        b2.setUser(user);
        b2.setViewCnt(0L);
        b2.setInDate(new Date());
        b2.setUpDate(new Date());
        boardRepository.save(b2);

        b1 = boardRepository.findById(1L).orElse(null);
        b2 = boardRepository.findById(2L).orElse(null);

        System.out.println("b1 = " + b1);
        System.out.println("b2 = " + b2);

        assertTrue(b1 != null);
        assertTrue(b2 != null);
    }
}
