package com.example.board.repository;

import com.example.board.model.Cart;
import com.example.board.model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
