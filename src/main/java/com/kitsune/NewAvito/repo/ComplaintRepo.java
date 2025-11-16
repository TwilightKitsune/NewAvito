package com.kitsune.NewAvito.repo;

import com.kitsune.NewAvito.models.Complaint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComplaintRepo extends CrudRepository<Complaint, Integer> {
    public List<Complaint> findAllByIdAnnouncement(Integer idAnnouncement);
}
