package ru.alcereo.supervisor.core.agents;

import ru.alcereo.supervisor.core.parsers.ShellParser;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * Created by alcereo on 08.07.17.
 */
public class DefaultShellAgent implements Agent{

    private Process process;
    private ShellParser parser = new ShellParser(this);
    private String name;

    public DefaultShellAgent() {
    }

    @Override
    public DefaultShellAgent destroy() {
        parser.deactivate();
        process.destroy();
        return this;
    }

    public DefaultShellAgent mountProcess(Process process) {
        this.process = process;
        parser.start();
        return this;
    }

    @Override
    public ShellParser getParser() {
        return parser;
    }

    @Override
    public void setParser(ShellParser parser) {
        this.parser = parser;
    }

    @Override
    public void parserDestroySignal() {
    }

    @Override
    public Integer getPID(){
        if(process.getClass().getName().equals("java.lang.UNIXProcess")) {
            /* get the PID on unix/linux systems */
            try {
                Field f = process.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                return f.getInt(process);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }

//    ┌--------------------------------------------------┐
//    |               process - DELEGATION               |
//    └--------------------------------------------------┘

    @Override
    public boolean isAlive() {
        return process.isAlive();
    }

    @Override
    public InputStream getInputStream() {
        return process.getInputStream();
    }

    @Override
    public InputStream getErrorStream() {
        return process.getErrorStream();
    }

    @Override
    public int getExitCode() {
        return process.exitValue();
    }

    @Override
    public OutputStream getOutputStream() {
        return process.getOutputStream();
    }

    @Override
    public int waitFor() throws InterruptedException {
        return process.waitFor();
    }

    @Override
    public boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
        return process.waitFor(timeout, unit);
    }

    @Override
    public int exitValue() {
        return process.exitValue();
    }

    @Override
    public Process destroyForcibly() {
        return process.destroyForcibly();
    }

//    -----------------------------------------------------

}
