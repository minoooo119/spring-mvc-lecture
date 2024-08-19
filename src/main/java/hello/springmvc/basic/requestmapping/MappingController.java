package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {
    final private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 기본 요청
     * 둘다 허용 /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
        //rest controller 이면 json 형태로 오류 메시지 보내준다.
    }

    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
        //rest controller 이면 json 형태로 오류 메시지 보내준다.
    }

    /**
     * 편리한 축약 애노테이션 (코드보기) * @GetMapping
     *
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    //path variable 스타일 매핑형태

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable String userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info("mappingPath={}", data);
        return "ok";
    }

    //path variable 다중 매핑

    /**
     * PathVariable 사용 다중
     * */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable("userId") String userID, @PathVariable("orderId") Long orderID) {
        log.info("mappingPath, userID={}, orderId={}", userID, orderID);
        return "ok";
    }
    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"} */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        // 특정 파라미터 정보가 있어서 호출이 되게끔 한다.
        // http://localhost:8080/mapping-param?mode=debug
        log.info("mappingParam");
        return "ok";
    }

    /**
     *특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = ) */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     * 만약 맞지 않으면 HTTP 415 상태코드(Unsupported Media Type)을 반환한다.
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        //post 하는 content type 이 application/json 형태여야 된다.
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     * produces = MediaType.TEXT_PLAIN_VALUE
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_PLAIN_VALUE)
    public String mappingProduces() {
        // post 에 대해서 받아들이는 content type 이 produces 와 같아야 한다. accept 헤더 확인 필요
        // 만약 맞지 않으면 HTTP 406 상태코드(Not Acceptable)을 반환한다.
        log.info("mappingProduces");
        return "ok";
    }
}
