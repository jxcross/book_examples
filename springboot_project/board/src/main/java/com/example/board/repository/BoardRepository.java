package com.example.board.repository;

import com.example.board.model.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/*
    // 논리
    findByTitleAndWriter

    // 등가 비교
    findByTitleIs
    findByTitleNot
    findByTitleIsNull
    findByTitleIsNotNull

    // 대소 비교
    findByBnoLessThan
    findByBnoLessThanEqual
    findByBnoGreaterThan
    findByBnoGreaterThanEqual

    // 날짜 비교
    findByInDateBefore
    findByInDateAfter

    // 와일드 카드
    findByTitleLike
    findByTitleStartingWith
    findByTitleEndingWith
    findByTitleContaining

    // order
    findByWriterOrderByTitleAscViewCntDesc


 */

public interface BoardRepository extends JpaRepository<Board, Long> {
    // select count(*) from board where writer = 'writer'
    int countAllByWriter(String writer);

    // select * from board where writer = 'writer'
    List<Board> findByWriter(String writer);

    // select * from board where title='title' and writer='writer'
    List<Board> findByTitleAndWriter(String title, String writer);

    // delete from board where writer='writer'
    @Transactional
    int deleteByWriter(String writer);

    // All Boards
    // 1) @Query(value="SELECT * FROM BOARD", nativeQuery=true)
    // 2) @Query("SELECT b FROM Board b")
    @Query("SELECT b FROM Board b")
    List<Board> findAllBoard();
    // 3) Pageable
    // Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "view_cnt");
    // List<Board> list = boardRepository.findTitleAndWriter(pageable);

    //List<Board> findAllBoard(Pageable pageable);

    // List<Sort.Order> sorts = new ArrayList<>();
    // sorts.add(new Sort.Order(Sort.Direction.DESC, "view_cnt");
    // sorts.add(new Sort.Order(Sort.Direction.ASC, "up_date");
    // Pageable pageable = PageRequest.of(0, 5, Sort.by(sorts));


    @Query(value="SELECT TITLE, WRITER FROM BOARD", nativeQuery = true)
    List<Object[]> findTitleAndWriter();


    // JPQL by Order
    @Query("SELECT b FROM Board b WHERE b.title=?1 AND b.writer=?2")
    List<Board> findByTitleAndWriter2(String title, String writer);

    // JPQL by Name
    @Query("SELECT b FROM Board b WHERE b.title=:title AND b.writer=:writer")
    List<Board> findByTitleAndWriter3(@Param("title") String title,
                                      @Param("writer") String writer);

}
