package com.wybase.trans.serve.controller;

import com.wybase.trans.base.exception.TransException;
import com.wybase.trans.base.result.Result;
import com.wybase.trans.base.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public Result test(@RequestParam String flag) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "18");
        if("0".equals(flag)){
            throw new TransException(ResultCodeEnum.ERROR);
        }
        if("1".equals(flag)){
            throw new TransException(ResultCodeEnum.FAIL,"");
        }
        return Result.fail().data("info",map);
    }
}
