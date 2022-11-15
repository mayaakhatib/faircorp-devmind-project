package com.emse.spring.faircorp.dao;

import com.emse.spring.faircorp.model.Heater;
import com.emse.spring.faircorp.model.Window;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RoomDaoCustomImpl implements RoomDaoCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Window> findWindowByRoomName(String name){
        String jpql = "select w from Window w where w.room.name = :name";
        return em.createQuery(jpql, Window.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Heater> findHeaterByRoomName(String name){
        String jpql = "select h from Heater h where h.room.name = :name";
        return em.createQuery(jpql, Heater.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void deleteByBuildingId(Long id){

    }
}
