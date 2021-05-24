package com.example.usermange.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Integer> {
    /*List<User> findAllByNameLike(String name, Pageable pageable);
    Page<User> findAllByEmailLike(String email, Pageable pageable);*/
    /*public List<User> findAllByOrderByNameDesc();*/
    /*@Query(value = "SELECT * FROM user ORDER BY name DESC", nativeQuery = true)
    public List<User> getListUserOrderByNameDesc();*/
    /*List<User> findAll(Sort sort);*/
    public User findByEmail(String email);
}
