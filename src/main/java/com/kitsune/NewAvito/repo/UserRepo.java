package com.kitsune.NewAvito.repo;

import com.kitsune.NewAvito.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {
    public List<User> findAllByLogin(String login);
}
