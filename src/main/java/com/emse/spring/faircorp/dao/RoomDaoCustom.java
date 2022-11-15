package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.Window;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RoomDaoCustom {

    List<Window> findWindowByRoomName(String name);

    List<Heater> findHeaterByRoomName(String name);

    @Transactional
    @Modifying
    @Query("delete from Room r where r.building.id = :id")
    void deleteByBuildingId(@Param("id") Long id);
}
