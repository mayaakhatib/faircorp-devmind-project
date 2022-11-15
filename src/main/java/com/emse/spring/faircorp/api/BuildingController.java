package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.*;
import com.emse.spring.faircorp.dao.BuildingDao;
import com.emse.spring.faircorp.dto.BuildingDto;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController // (1)
@RequestMapping("/api/buildings") // (2)
@Transactional // (3)
public class BuildingController {
    private final BuildingDao buildingDao;
    private final RoomDao roomDao;
    private final HeaterDao heaterDao;
    private final WindowDao windowDao;

    public BuildingController(BuildingDao buildingDao, RoomDao roomDao, HeaterDao heaterDao, WindowDao windowDao){
        this.buildingDao = buildingDao;
        this.roomDao = roomDao;
        this.heaterDao = heaterDao;
        this.windowDao = windowDao;
    }


    @GetMapping // (5)
    public List<BuildingDto> findAll() {
        return buildingDao.findAll().stream().map(BuildingDto::new).collect(Collectors.toList());  // (6)
    }

    @GetMapping(path = "/{id}")
    public BuildingDto findById(@PathVariable Long id) {
        return buildingDao.findById(id).map(BuildingDto::new).orElse(null); // (7)
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {

        Building building = buildingDao.getById(id);

        /* delete all windows and heaters in the rooms of the building */
        for (Room room: building.getRooms()) {
            windowDao.deleteByRoom(room.getId());
            heaterDao.deleteByRoom(room.getId());
        }

        /* delete all the rooms in the building */
        roomDao.deleteByBuildingId(id);

        /* delete the building */
        buildingDao.deleteById(id);
    }

    @PostMapping // (8)
    public BuildingDto create(@RequestBody BuildingDto dto) {
        // WindowDto must always contain the window room
        //Room room = roomDao.getById(dto.getRoomId());
        Building building = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            building = buildingDao.save(new Building( dto.getName(), dto.getOutsideTemperature()));
        }
        else {
            building = buildingDao.getById(dto.getId());  // (9)
            building.setName(dto.getName());
            building.setOutsideTemperature(dto.getOutsideTemperature());
        }
        return new BuildingDto(building);
    }

}
