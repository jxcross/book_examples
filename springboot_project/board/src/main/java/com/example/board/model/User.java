package com.example.board.model;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

 /*
    @Entity
    @Id
    @Table(name, catalog, schema, uniqueConstraint)
    @Column(name, unique, nullable, insertable, updatable, columnDefinition, length, precision, scale)
    @Enumerated
    @Temporal(DATE, TIME, TIMESTAMP)
    @Transient: 제외 속성
    @Generated(strategy, generator), [TABLE, SEQUENCE, IDENTITY, AUTO]

    EntityManager em = entityManagerFactory.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(user); em.remove(user); em.flush();
    tx.commit(); tx.rollback(); em.close();
 */

@Entity
public class User {
    @Id
    @Column(name="user_id")
    private String id;
    private String password;
    private String name;
    private String email;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date inDate;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date upDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<Board> list = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public List<Board> getList() {
        return list;
    }

    public void setList(List<Board> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", inDate=" + inDate +
                ", upDate=" + upDate +
                ", list=" + list +
                '}';
    }
}
