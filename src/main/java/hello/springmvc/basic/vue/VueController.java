package hello.springmvc.basic.vue;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class VueController {
    private static final Logger log = LoggerFactory.getLogger(VueController.class);

    @GetMapping("/vuePage")
    public String vuePage() {
        log.info("request On Vue");
        return "vue/index";
    }
}
