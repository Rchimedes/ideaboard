package com.lancewade.beltexam.repositories;

import com.lancewade.beltexam.models.UserIdea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdeaRepository extends CrudRepository<UserIdea, Long> {
}
