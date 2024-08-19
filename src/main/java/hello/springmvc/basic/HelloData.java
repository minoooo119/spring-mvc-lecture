package hello.springmvc.basic;

import lombok.Data;

@Data//getter, setter 등등 자동으로 만들어 준다.
public class HelloData {
    private String username;
    private int age;
}
