package com.nujitha.project.controllers;

import com.nujitha.project.firebaseUtils.FirebaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/bookinglist")
public class ScheduleController {
    private FirebaseUtil firebaseUtil;

    @Autowired
    public ScheduleController(FirebaseUtil firebaseUtil) {
        super();
        this.firebaseUtil = firebaseUtil;
    }

    @GetMapping
    public String bookedCollection(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("bookinglist", FirebaseUtil.bookingObjects());
        return "bookinglist";
    }
}
