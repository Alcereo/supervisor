package ru.alcereo.supervisor.core.runners.springboot;

import ru.alcereo.supervisor.core.agents.springboot.SpringBootTomcatAgent;
import ru.alcereo.supervisor.core.runners.AbstractShellRunner;

import java.io.File;

/**
 * Created by alcereo on 08.07.17.
 */
public class SpringBootTomcatJavaJarRunner extends AbstractShellRunner<SpringBootTomcatAgent> {

    @Override
    protected SpringBootTomcatAgent createNewAgent() {
        return new SpringBootTomcatAgent();
    }

    public SpringBootTomcatJavaJarRunner(File projectDir) {
        super(new ProcessBuilder());
        getProcessBuilder().directory(
                projectDir.toPath()
                        .resolve("build")
                        .resolve("libs")
                        .toFile()
        );
        getProcessBuilder().command(
                "sh",
                "-c",
                "java -jar $(ls -At | grep /*\\.jar$)"
        );
    }

}
