package com.zz.playground.webapp.controller;

import com.zz.playground.webapp.controller.exception.CaughtException;
import com.zz.playground.webapp.controller.exception.PermissionException;
import com.zz.playground.webapp.controller.exception.ThrownException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luganlin on 31/05/2018.
 */

@RestController
public class Controller {
    @RequestMapping(value = "/name")
    public String getName() {
        return "John";
    }

    @RequestMapping(value = "/exception/catch")
    public String cacheException() {
        throw  new CaughtException("exception has been caught");
        //return "John";
    }

    @RequestMapping(value = "/exception/throw")
    public String throwException() {
        throw  new ThrownException("exception has been thrown");
    }
}
