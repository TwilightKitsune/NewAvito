package com.kitsune.NewAvito.repo;

import com.kitsune.NewAvito.models.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepo extends CrudRepository<Admin, Integer> {
    public List<Admin> findAllByLogin(String login);
}
