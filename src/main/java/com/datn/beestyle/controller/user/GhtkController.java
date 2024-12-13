package com.datn.beestyle.controller.user;


import com.datn.beestyle.service.ghtk.GhtkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping("/ghtk")
public class GhtkController {


    private final GhtkService ghtkService;

    public GhtkController(GhtkService ghtkService) {
        this.ghtkService = ghtkService;
    }

    @PostMapping("/calculate-fee")
    public ResponseEntity<String> calculateShippingFee(@RequestBody Map<String, Object> request) {
        // Gọi service để tính phí vận chuyển
        return ghtkService.calculateShippingFee(request);
    }
}
