package hello.springmvc.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {
    private static final Logger log = LoggerFactory.getLogger(RequestBodyStringController.class);

    // HTTP 요청 메시지에 단순 메시지
    // 직접 json 이나 xml, text 로 데이터를 직접 담아서 요청시에
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //이렇게 하면 messageBody 가져올 수 있다.
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        //messageBody=hello
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    //그냥 servlet 필요없이 바로 이렇게 받을 수 있다.
    public void requestBodyStringV2(InputStream inputStream, Writer writer) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        //messageBody=hello
        writer.write("ok");
    }

    //근데 stream 사용하는 것도 번거로울 수 있다.
    //converter 를 사용하면 된다.

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
        //HttpEntity<String> 이렇게 하면 http body 를 String 으로 알아서 convert 해준다.

        // Http Header 랑 body 편하게 조회할 수 있다. 파라미터 조회랑은 아무런 관련이 없다.
        // 파라미터 관련은 query parameter 의 경우에 사용하는 것이다.

        //httpEntity 상속한 RequestEntity, ResponseEntity => (상태코드 사용가능) 사용할 수 있다.

        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);


        //반환은 어떻게 하는가!
        //첫 파라미터가 body 된다. 그래서 request body 에 ok 넣어 보내는 것 처럼 보인다.
        return new HttpEntity<>("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody){
        //@RequestBody 사용시 알아서 requestBody Message 를 string 으로해서 전달해준다.
        log.info("messageBody={}", messageBody);
        return "ok"; //@ResponseBody 를 통해서 responseBody Message 를 "ok" 로 넣어준다.
    }

}

