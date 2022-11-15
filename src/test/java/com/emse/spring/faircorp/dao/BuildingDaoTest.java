
package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Window;
import com.emse.spring.faircorp.model.WindowStatus;
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
class BuildingDaoTest {

    @Autowired
    private HeaterDao heaterDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private WindowDao windowDao;

    @Autowired
    private BuildingDao buildingDao;

    @Test
    public void shouldFindWindowByBuildingId() {
        List<Window> result = buildingDao.findWindowsByBuildingId((long) -10);
        Assertions.assertThat(result)
                .hasSize(2)
                .extracting("id", "windowStatus")
                .containsExactly(Tuple.tuple(-10L, WindowStatus.CLOSED),Tuple.tuple(-9L, WindowStatus.CLOSED));
    }

    @Test
    public void shouldFindHeaterByBuildingId() {
        List<Heater> result = buildingDao.findHeatersByBuildingId((long) -10);
        Assertions.assertThat(result)
                .hasSize(2);
    }



}