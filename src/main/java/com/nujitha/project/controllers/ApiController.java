package com.nujitha.project.controllers;

import com.nujitha.project.classes.Schedule;
import com.nujitha.project.firebaseUtils.FirebaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*", allowedHeaders = "*") //cross origin resource sharing
@RestController
@RequestMapping("/api")
public class ApiController {

    private FirebaseUtil firebaseUtil;

    @Autowired // injecting object dependencies
    public ApiController(FirebaseUtil firebaseUtil) {
        super();
        this.firebaseUtil = firebaseUtil;
    }

    @GetMapping("/vehicles")
    public List getToArray() throws ExecutionException, InterruptedException {
        return FirebaseUtil.getToArray();
    }

    @PostMapping("/booking")
    public void createBooking(@RequestBody Schedule schedule) {
        FirebaseUtil.addBooking(schedule);
    }

    @GetMapping("/bookinglist")
    public List bookedCollection() throws ExecutionException, InterruptedException {
        return FirebaseUtil.bookingObjects();
    }
}
