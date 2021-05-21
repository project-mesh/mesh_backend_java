package com.mesh.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/HelloWorld")
public class HelloWorldController {

    public static class HelloWorld{
        public String message;

        public HelloWorld(){
            this.message = "Hello World!";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object helloWorldGet(){
        return new HelloWorld();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object helloWordPost(@RequestBody HelloWorld helloWorld){
        return helloWorld;
    }
}
