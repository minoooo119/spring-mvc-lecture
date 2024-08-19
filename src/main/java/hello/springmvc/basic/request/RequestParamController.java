package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Controller
public class RequestParamController {

    private static final Logger log = LoggerFactory.getLogger(RequestParamController.class);

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        PrintWriter writer = response.getWriter();
        writer.write("ok");
        //http://localhost:8080/request-param-v1?username=mino&&age=17
        //http://localhost:8080/request-param-v1 하고 request body 에 담아 보낸다.

    }
}
