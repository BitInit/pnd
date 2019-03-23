package site.bitinit.pnd.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: john
 * @date: 2019/3/23
 */
@RestController
public class TestController {

    @GetMapping("/ok")
    public String ok(){
        return "ok";
    }
}
