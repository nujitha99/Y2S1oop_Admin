package com.nujitha.project.controllers;

import com.nujitha.project.firebaseUtils.FirebaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    private FirebaseUtil firebaseUtil;

    @Autowired
    public VehicleController(FirebaseUtil firebaseUtil) {
        super();
        this.firebaseUtil = firebaseUtil;
    }

    @GetMapping
    public String getToArray(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("vehicles", FirebaseUtil.getToArray());
        return "vehicles";
    }
}
