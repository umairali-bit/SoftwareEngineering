package com.example.Auditing.Auditing.controllers;


import com.example.Auditing.Auditing.entities.PostEntity;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/audit")
public class AuditController {
    /*
this controller is for admins. we can use PostEntity directly instead of using postdto because admins can see anything
   tracking the changes to 1 particular id
   to track the changes we need to read all the changes
   for that we are using AutoReaderClass and autowiring EntityManagerFactory
   */

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @GetMapping(path = "/posts/{postId}")
    List<PostEntity> getPostRevisions(@PathVariable Long postId) {
        AuditReader reader = AuditReaderFactory.get(entityManagerFactory.createEntityManager());
       List<Number> revisions = reader.getRevisions(PostEntity.class, postId);
       return revisions
               .stream()
               .map(revisionNumber -> reader.find(PostEntity.class, postId, revisionNumber))
               .collect(Collectors.toList());



    }


}
