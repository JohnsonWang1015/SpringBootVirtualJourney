package com.nuu.service;

import com.nuu.dao.AttractionsDao;
import com.nuu.entity.Attractions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttractionsService {

    @Autowired
    private AttractionsDao attractionsDao;

    @GetMapping(path = "/api/spot", produces = "application/json")
    public ResponseEntity<List<Attractions>> queryAllAttractions(){
        List<Attractions> result = attractionsDao.selectAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
