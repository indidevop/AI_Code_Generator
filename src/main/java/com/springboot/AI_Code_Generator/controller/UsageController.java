package com.springboot.AI_Code_Generator.controller;

import com.springboot.AI_Code_Generator.dto.subscription.PlanLimitsResponse;
import com.springboot.AI_Code_Generator.dto.subscription.UsageTodayResponse;
import com.springboot.AI_Code_Generator.security.AuthUtil;
import com.springboot.AI_Code_Generator.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usage")
public class UsageController {

    private final UsageService usageService;
    private final AuthUtil authUtil;

//    @GetMapping("/today")
//    public ResponseEntity<UsageTodayResponse> checkDailyTokensUsage(){
//        return ResponseEntity.ok(usageService.checkDailyTokensUsage());
//    }
//
//    @GetMapping("/limits")
//    public ResponseEntity<PlanLimitsResponse> getPlanLimits(){
//        Long userId=authUtil.getCurrentUserId();
//        return ResponseEntity.ok(usageService.getPlanLimits(userId));
//    }

}
