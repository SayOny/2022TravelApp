package com.project.travel.repository;

import com.project.travel.domain.Post;
import com.project.travel.domain.Tag;
import com.project.travel.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Tag tag){
        em.persist(tag);
    }
    public Tag findByName(String sTag){
        return em.createQuery("select u from Tag u where u.tagContent= :sTag",Tag.class)
                .setParameter("sTag",sTag)
                .getSingleResult();
    }

}
