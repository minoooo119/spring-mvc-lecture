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

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(value = "username", required = true) String username,
                                       @RequestParam(value = "age",required = false) Integer age
                                       //int 에는 null 을 넣지 못해서 Integer 로 변경해야한다.
                                       ) {
        //Required request parameter 'username' for method parameter type String is not present]
        //이런식으로 필수 값 빠뜨리면 에러가 난다.

//        `/request-param-required?username=`
//        파라미터 이름만 있고 값이 없는 경우 빈문자로 통과 -> null 이 아니라 빈문자라는 값으로 인식

        log.info("username={}, age={}", username,age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용 *
     * 참고: defaultValue는 빈 문자의 경우에도 적용 * /request-param-default?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
//            이미 기본 값이 있기 때문에 `required` 는 의미가 없다.
//            `defaultValue` 는 빈 문자의 경우에도 설정한 기본 값이 적용된다.
            @RequestParam(required = true, defaultValue = "guest", value = "username") String username,
            @RequestParam(required = false, defaultValue = "-1", value = "age") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }
}
