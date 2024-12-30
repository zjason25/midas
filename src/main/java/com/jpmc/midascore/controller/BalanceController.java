package com.jpmc.midascore.controller;

import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private UserService userService;

    @GetMapping("/balance")
    public Balance getUserBalance(@RequestParam("userId") long userId) {
        UserRecord user = userService.findUserById(userId);
        float balance = (user != null) ? user.getBalance() : 0;
        return new Balance(balance);
    }
}
