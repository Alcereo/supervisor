package ru.alcereo.supervisor.core.tests.main;

import org.junit.Test;
import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import ru.alcereo.supervisor.core.runners.DefaultShellRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by alcereo on 22.07.17.
 */
public class DefaultShellAgentTest {

    private DefaultShellAgent agent;

    @Test
    public void SuccessRunAgentWithWorker() throws IOException, InterruptedException {

        String testLine = "Some test string";

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
                "sh",
                "-c",
                "echo '"+testLine+"'"
        );

        DefaultShellRunner runner = new DefaultShellRunner(processBuilder);

        List<String> stdoutString = new ArrayList<>();
        runner.addWorker(stdoutString::add);

        runner.runNewAgent().getParser().waitFor();

        assertEquals(1,stdoutString.size());
        assertEquals(testLine, stdoutString.get(0));

    }

    @Test
    public void SuccessDestroyAgent() throws IOException, InterruptedException {

        String testLine = "Some test string";

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
                "sh",
                "-c",
                "while :; do sleep 1; echo '"+testLine+"'; done"
        );

        DefaultShellRunner runner = new DefaultShellRunner(processBuilder);
        runner.runNewAgent()
                .destroy()
                .waitFor();
    }

}
