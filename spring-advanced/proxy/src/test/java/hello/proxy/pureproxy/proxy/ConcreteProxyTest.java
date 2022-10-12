package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.proxy.concreteproxy.code.ConcreteLogic;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

}
