package ru.alcereo.supervisor.core.tests.main;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import ru.alcereo.supervisor.core.agents.exceptions.ProcessDiedException;
import ru.alcereo.supervisor.core.agents.springboot.AgentState;
import ru.alcereo.supervisor.core.agents.springboot.SpringBootTomcatAgent;
import ru.alcereo.supervisor.core.runners.springboot.SpringBootTomcatJavaJarRunner;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created by alcereo on 07.07.17.
 */
public class IntegrationTestCase {

    private static SpringBootTomcatAgent agent;

    @BeforeClass
    public static void startTheApplication() throws IOException {
        SpringBootTomcatJavaJarRunner runner = new SpringBootTomcatJavaJarRunner(Paths.get("..","tests","spring-boot-sample-service").toFile());

//        runner.setFileName("sample-web-app-0.0.1-SNAPSHOT.jar");
//        runner.setProjectDir(new File("sample-web-app"));

        agent = runner.runNewAgent();
        agent.getParser().addWorker(System.out::println);

        try {
            agent.waitForState(AgentState.STARTED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ProcessDiedException e) {
            throw new RuntimeException("Application start error!", e);
        }

        System.out.println("Success starting app");
    }

    @AfterClass
    public static void destroyTheApp(){
        agent.destroy();
    }

    @Test
    public void testSomeRequest(){
        RestTemplate template = new RestTemplate();
        String responseText = template.getForObject("http://localhost:8080/test-get", String.class);
        assertEquals(responseText, "ok!");
    }

}
