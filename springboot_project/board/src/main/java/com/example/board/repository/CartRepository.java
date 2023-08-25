package com.example.board.repository;

import com.example.board.model.Cart;
import org.springframework.data.repository.CrudRepository;

import javax.smartcardio.Card;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
