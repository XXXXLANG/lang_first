package org.example.LiveSystem.controller;

import org.example.LiveSystem.entity.User;
import org.example.LiveSystem.service.UsersService;
import org.example.LiveSystem.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("test")
public class TestController {

    // @RequestMapping(value="t1",method={RequestMethod.GET})
    @GetMapping("t1")
    public String test1(){
        return "test1 ok";
    }

    @Autowired
    private UsersService usersService;

    // http://localhost:8080/test/getuser?id=1
    @GetMapping("/getuser")
    public User getById(int id){
        return usersService.findById(id);
    }

//    群发测试,不能公开
//    @Autowired
//    private WebSocketService webSocketService;
//
//    // http://localhost:8080/test/sendMessage?message=1
//    @GetMapping("/sendMessage")
//    public String sendMessage(String message) {
//        webSocketService.GroupSending(message);
//        return message;
//    }


}