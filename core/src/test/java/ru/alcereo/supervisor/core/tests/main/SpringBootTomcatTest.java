package ru.alcereo.supervisor.core.tests.main;

import org.junit.Test;
import ru.alcereo.supervisor.core.agents.exceptions.ProcessDiedException;
import ru.alcereo.supervisor.core.agents.springboot.AgentState;
import ru.alcereo.supervisor.core.runners.springboot.SpringBootTomcatJavaJarRunner;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by alcereo on 22.07.17.
 */
public class SpringBootTomcatTest {

    @Test
    public void SuccessStartApp() throws IOException, InterruptedException, ProcessDiedException {

        SpringBootTomcatJavaJarRunner runner =
                new SpringBootTomcatJavaJarRunner(
                        Paths.get("..",
                                "tests",
                                "spring-boot-sample-service"
                        ).toFile()
                );

        runner.addWorker(System.out::println);
        runner.runNewAgent()
                .waitForState(AgentState.STARTED)
                .destroy()
                .waitFor();

    }
}
