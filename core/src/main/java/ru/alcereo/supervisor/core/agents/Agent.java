package ru.alcereo.supervisor.core.agents;

import ru.alcereo.supervisor.core.parsers.ShellParser;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by alcereo on 07.07.17.
 */
public interface Agent {

    Integer getPID();

    boolean isAlive();

    InputStream getInputStream();

    InputStream getErrorStream();

    int getExitCode();

    DefaultShellAgent destroy();

    ShellParser getParser();

    void setParser(ShellParser parser);

    void parserDestroySignal();

    OutputStream getOutputStream();

    int waitFor() throws InterruptedException;

    boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException;

    int exitValue();

    Process destroyForcibly();

}
