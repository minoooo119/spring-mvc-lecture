package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 그냥 Controller 로 하면 리턴이 원래 뷰 이름이다.
 * RestController 사용하면 message body 에 리턴값 그대로 넣는다.
 */
@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        //LOG 정보에 대해서 알 수 있다.
        //정도에 맞게 로그를 출력해보기 위해서 이렇게 하는게 좋다.
        log.trace("trace log={}",name);
        log.debug("debug log={}",name);
        log.info("info log={}",name);
        log.warn("warn log={}",name);
        log.error("error log={}",name);

        //2024-08-19T15:14:11.652+09:00  INFO 8426 --- [springmvc] [nio-8080-exec-2]
        // h.springmvc.basic.LogTestController : info log=Spring

        return "ok";
    }
}
