package cn.kiway.bxm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @author: yimin
 * @Date: 2018/9/7
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public void index(HttpServletResponse response){
        try {
//            response.sendRedirect("static/yixiakuan/index.html");
            response.sendRedirect("yixiakuan/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
