package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {
    private static final Logger log = LoggerFactory.getLogger(RequestBodyJsonController.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    //잘되긴하지만 !!!!! -> objectMapper 로 하는게 참 번거로워 ...
    /**
     * @RequestBody
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용 *
     * @ResponseBody
     * - 모든 메서드에 @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={},age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:
    application/json)
     *
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    //이렇게해도 동작한다..?
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {//HttpEntity<HelloData> 로 해도 된다.

        //HttpMassageConverter 가 content type 이 json 인 경우에 MappingJackson2HttpMessageConverter 를 가져오고
        //해당 Converter 에서 objectMapper 를 통해 했던 부분들을 알아서 진행해서 helloData 에 전달을 해주게 된다.

        log.info("username={},age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
//        HTTP 요청시에 content-type이 application/json인지 꼭! 확인해야 한다.
//        그래야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.

    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    //이렇게해도 동작한다
    public String requestBodyJsonV3(HttpEntity<HelloData> messageBody) {//HttpEntity<HelloData> 로 해도 된다.

        //HttpMassageConverter 가 content type 이 json 인 경우에 MappingJackson2HttpMessageConverter 를 가져오고
        //해당 Converter 에서 objectMapper 를 통해 했던 부분들을 알아서 진행해서 helloData 에 전달을 해주게 된다.
        HelloData helloData = messageBody.getBody();
        log.info("username={},age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type:
    application/json)
     *
     * @ResponseBody 적용
     * - 메시지 바디 정보 직접 반환(view 조회X)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용(Accept:
    application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }
    //converter 가 response 로 보낼때에도 적용이 된다. 그래서 json 으로 바꿔서 내보내준다.
}
