package com.example.demo.repository;

import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        try {
            return em.createQuery("select u from User u where u.id = :id and u.isDeleted = false", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public void remove(Long id) {
        em.find(User.class, id).setDeleted(true);
    }

    public Boolean isDeleted(Long id) {
        return em.find(User.class, id).isDeleted() == true;
    }

    public Optional<User> findByEmail(String email) {
        List<User> members =  em.createQuery("select u from User u where u.email = :email and u.isDeleted = false", User.class)
                .setParameter("email", email)
                .getResultList();
        return members.stream().findAny();
    }
}
