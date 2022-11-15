package com.emse.spring.faircorp.api;

import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.HeaterDto;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.dto.WindowDto;
import com.emse.spring.faircorp.model.*;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController // (1)
@RequestMapping("/api/rooms") // (2)
@Transactional // (3)
public class RoomController {

    private final HeaterDao heaterDao;
    private final RoomDao roomDao;

    private final WindowDao windowDao;

    public RoomController(RoomDao roomDao, HeaterDao heaterDao, WindowDao windowDao) { // (4)
        this.heaterDao = heaterDao;
        this.roomDao = roomDao;
        this.windowDao=windowDao;
    }

    @GetMapping // (5)
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());  // (6)
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null); // (7)
    }

    @PostMapping // (8)
    public RoomDto create(@RequestBody RoomDto dto) {
        // WindowDto must always contain the window room
        //Room room = roomDao.getById(dto.getRoomId());
        Room room = null;
        // On creation id is not defined
        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getFloor(), dto.getName(), dto.getCurrentTemperature(), dto.getTargetTemperature()));
        }
        else {
            room = roomDao.getById(dto.getId());  // (9)
            room.setName(dto.getName());
            room.setFloor(dto.getFloor());
            room.setCurrentTemperature(dto.getCurrentTemperature());
            room.setTargetTemperature(dto.getTargetTemperature());
        }
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        windowDao.deleteByRoom(id);
        heaterDao.deleteByRoom(id);
        roomDao.deleteById(id);
    }

    @PutMapping(path = "/{id}/switchWindows")
    public RoomDto switchWindowsStatus(@PathVariable Long id) {
        Room room= roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        for ( Window window: room.getWindows() ) {
            window.setWindowStatus(window.getWindowStatus() == WindowStatus.OPEN ? WindowStatus.CLOSED : WindowStatus.OPEN);
        }
        return new RoomDto(room);
    }

    @PutMapping(path = "/{id}/switchHeaters")
    public RoomDto switchHeatersStatus(@PathVariable Long id) {
        Room room= roomDao.findById(id).orElseThrow(IllegalArgumentException::new);
        for ( Heater heater: room.getHeaters() ) {
            heater.setHeaterStatus(heater.getHeaterStatus() == HeaterStatus.ON ? HeaterStatus.OFF : HeaterStatus.ON);
        }
        return new RoomDto(room);
    }


}
