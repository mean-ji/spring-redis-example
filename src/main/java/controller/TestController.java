package controller;

import domain.User;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import service.TestCacheService;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestCacheService testCacheService;

    @RequestMapping("/test")
    public void setUsers() {
        testCacheService.setUsers();
    }

    @RequestMapping(value = "/get", produces="application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity getUsers() throws ParseException {
        return ResponseEntity.ok(testCacheService.getUsers());
    }

    @RequestMapping("/test-refresh")
    public void refreshUsers() {
        testCacheService.removeCacheUsers();
        testCacheService.setUsers();
    }
}
