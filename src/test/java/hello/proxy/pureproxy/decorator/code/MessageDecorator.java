package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");
        String operation = component.operation();
        String result="****"+operation+"****";

        log.info("MessageDecorator 적용 , 적용전 ={}  적용 후={}",operation,result);

        return result;

    }
}
