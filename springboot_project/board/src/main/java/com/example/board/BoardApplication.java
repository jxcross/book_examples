package com.example.board;

import com.example.board.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Date;

@SpringBootApplication
public class BoardApplication implements CommandLineRunner {
	@Autowired
	EntityManagerFactory emf;

	public static void main(String[] args) {

		SpringApplication.run(BoardApplication.class, args);
		//app.setWebApplicationType(WebApplicationType.NONE); app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction tx = em.getTransaction();
//
//		System.out.println("emf = " + emf);
//		System.out.println("em = " + em);
//
//		User user = new User();
//		user.setId("user1");
//		user.setPassword("1234");
//		user.setName("user1");
//		user.setEmail("user1@email.com");
//		user.setInDate(new Date());
//		user.setUpDate(new Date());
//
//		tx.begin();
//		em.persist(user);
//		tx.commit();
//
//		System.out.println("user = " + em.find(User.class, "user1"));
	}
}
