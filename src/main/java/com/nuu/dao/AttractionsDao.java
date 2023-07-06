package com.nuu.dao;

import com.nuu.components.IDaoOperations;
import com.nuu.entity.Attractions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttractionsDao implements IDaoOperations<Attractions, Integer> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(Attractions source) {
        return false;
    }

    @Override
    public Attractions selectForObject(Integer key) {
        String sql = "Select * From attractions Where id=?;";
        try{
            Attractions result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Attractions.class), new Object[]{key});
            if(result != null){
                return result;
            }
        }catch (DataAccessException ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public List<Attractions> selectAll() {
        String sql = "Select * From attractions;";
        try{
            List<Attractions> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Attractions.class));
            if(result.size() > 0){
                return result;
            }
        }catch (DataAccessException ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public boolean update(Attractions source) {
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        return false;
    }
}
