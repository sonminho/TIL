package hello.proxy.decorator.codes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageDecorator implements Component {

    private final Component component;

    @Override
    public String operation() {
        log.info("MessageDecorator 실행");
        String result = component.operation();
        String decoResult = "***" + result + "***";
        log.info("MessageDecorator 적용 전 = {}, 적용 후 = {}", result, decoResult);
        return decoResult;
    }

}
