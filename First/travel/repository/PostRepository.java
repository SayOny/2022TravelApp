package com.project.travel.repository;

import com.project.travel.domain.Post;
import com.project.travel.domain.Tag;
import com.project.travel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepository{

    @PersistenceContext
    private EntityManager em;

    public void save(Post post){
        em.persist(post);
    }
    public Post findOne(Long id){
        Post post=em.find(Post.class, id);
        return post;
    }

    public List<Post> findAll() {
        return em.createQuery("select u from Post u order by u.like desc", Post.class)
                .getResultList();
    }

    public List<Post> findByHashtag(Tag tag){
        String t = tag.getTagContent();
        return em.createQuery("select u from Post u where u.tags= : t", Post.class)
                .setParameter("t",t)
                .getResultList();
    }




}
