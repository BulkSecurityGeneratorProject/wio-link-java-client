package nl.openweb.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Component;

import nl.openweb.iot.wio.rest.NodeResource;
import nl.openweb.iot.wio.rest.SeeedSsoResource;

@SpringBootApplication
@EnableFeignClients
public class WioLinkClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WioLinkClientDemoApplication.class, args);
    }
}

@Component
class TestRunner implements CommandLineRunner {

    private SeeedSsoResource userResource;
    private NodeResource nodeResource;

    @Autowired
    public TestRunner(SeeedSsoResource userResource, NodeResource nodeResource) {
        this.userResource = userResource;
        this.nodeResource = nodeResource;
    }

    @Override
    public void run(String... args) throws Exception {
        SeeedSsoResource.LoginResponse response = userResource.login(args[0], args[1]);
        System.out.println(nodeResource.list(response.getToken()));
    }
}

