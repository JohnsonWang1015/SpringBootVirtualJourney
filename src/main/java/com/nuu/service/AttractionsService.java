package com.nuu.service;

import com.nuu.dao.AttractionsDao;
import com.nuu.entity.Attractions;
import com.nuu.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AttractionsService {

    @Autowired
    private AttractionsDao attractionsDao;
    private List<String> historySpot = new ArrayList<>();

    @GetMapping(path = "/api/spot", produces = "application/json")
    public ResponseEntity<List<Attractions>> queryAllAttractions(){
        List<Attractions> result = attractionsDao.selectAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/api/spot/{id}", produces = "application/json")
    public Attractions queryByAttractionId(@PathVariable(name = "id") Integer id){
        Attractions result = attractionsDao.selectForObject(id);
        return result;
    }

    @GetMapping(path = "/api/spot/{name}/search", produces = "application/json")
    public ResponseEntity<Attractions> queryByAttractionName(@PathVariable(name = "name") String name){
        if(name == null){
            System.out.println("無資料");
        }else{
            Attractions result = attractionsDao.selectForAttractionName(name);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/api/spot", produces = "application/json")
    public List<String> addSpotToList(@RequestBody Question question){
        historySpot.add(question.getQuestion());
        System.out.println(historySpot);
        return historySpot;
    }

    @PutMapping(path = "/api/spot")
    public void exit(){
        historySpot.clear();
        System.out.println(historySpot);
    }

    @GetMapping(path = "/api/spot/history", produces = "application/json")
    public List<String> getHistorySpot(){
        return historySpot;
    }

}
