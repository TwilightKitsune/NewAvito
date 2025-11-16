package com.kitsune.NewAvito.repo;

import com.kitsune.NewAvito.models.Announcement;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepo extends CrudRepository <Announcement, Integer>{
    @Query("SELECT * FROM Announcement WHERE idUsers = :idUsers")
    public List<Announcement> findAllByIdUsers(@Param("idUsers") Integer idUsers);

    @Query("SELECT * FROM Announcement WHERE verified_by_admin = :verifiedByAdmin")
    public List<Announcement> findAllByVerifiedByAdmin(@Param("verified_by_admin") Boolean verifiedByAdmin);

    public List<Announcement> findAllByType(String type);
}
