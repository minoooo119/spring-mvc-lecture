package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ResponseBody //이거 적으면 리턴 값을 그대로 response body 에 넣어버린다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    //스프링 부트 3.2부터 자바 컴파일러에 -parameters 옵션을 넣어주어야 애노테이션에 적는 이름을 생략할 수 있다.
    //그냥 버전2로 해결하자

//    @ResponseBody//조금 생략해보기
//    @RequestMapping("/request-param-v3")
//    public String requestParamV3(@RequestParam String username,
//                                 @RequestParam int age) {
//
//        log.info("username={}, age={}", username, age);
//        return "ok";
//    }
//
//    @ResponseBody//조금 생략해보기
//    @RequestMapping("/request-param-v4")
//    public String requestParamV4(String username,
//                                  int age) {
//
//        log.info("username={}, age={}", username, age);
//        return "ok";
//    }
}
