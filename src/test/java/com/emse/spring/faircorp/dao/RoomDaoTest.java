package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoomDaoTest {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void shouldFindWindowByRoomName() {
        List<Window> result = roomDao.findWindowByRoomName("Room2");
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-8L, WindowStatus.OPEN),Tuple.tuple(-7L, WindowStatus.CLOSED));
    }

    @Test
    public void shouldFindHeaterByRoomName() {
        List<Heater> result = roomDao.findHeaterByRoomName("Room1");
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "heaterStatus")
                .containsExactly(Tuple.tuple(-10L, HeaterStatus.ON),Tuple.tuple(-9L, HeaterStatus.ON));
    }


}
