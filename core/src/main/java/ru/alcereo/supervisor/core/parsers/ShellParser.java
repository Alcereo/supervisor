package ru.alcereo.supervisor.core.parsers;

import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import ru.alcereo.supervisor.core.workers.ParserWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by alcereo on 08.07.17.
 */
public class ShellParser {

    private List<ParserWorker> workersList = new CopyOnWriteArrayList<>();
    private Thread parseTread;
    private DefaultShellAgent agent;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private volatile boolean workerPoolTerminated = true;
    private final Object workerPoolTerminatedMonitor = new Object();
    private volatile boolean isActive = false;
    private final Object isActiveMonitor = new Object();

    public ShellParser(DefaultShellAgent agent) {
        this.agent = agent;
    }

    public ShellParser() {
    }

    public void start(){

        synchronized (workerPoolTerminatedMonitor){
            workerPoolTerminated = false;
        }
        synchronized (isActiveMonitor){
            isActive = true;
        }

        parseTread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(agent.getInputStream()));

            String line;

            System.out.println("Now here:"+Thread.currentThread().getName());

            try {
                while ((line=reader.readLine())!=null && isActive){
                    final String fline = line;

                    workersList
                            .forEach(
                                    parserWorker ->
                                            executorService.execute(
                                                    () -> parserWorker.workForLine(fline)
                                            )
                            );
                }
            } catch (IOException e) {
                System.out.println("Read interrupted");
            }

            System.out.println("shutdown parser");

            executorService.shutdown();
            try {
                executorService.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (workerPoolTerminatedMonitor){
                workerPoolTerminated = true;
                workerPoolTerminatedMonitor.notifyAll();
            }
            synchronized (isActiveMonitor){
                isActive = false;
                isActiveMonitor.notifyAll();
            }

            agent.parserDestroySignal();
        });

        parseTread.setDaemon(true);
        parseTread.start();
    }

    public Thread getParseTread() {
        return parseTread;
    }

    public ShellParser setAgent(DefaultShellAgent agent) {
        this.agent = agent;
        return this;
    }

    public List<ParserWorker> getWorkersList() {
        return workersList;
    }

    public void setWorkersList(List<ParserWorker> workersList) {
        this.workersList = workersList;
    }

    public ShellParser addWorker(ParserWorker worker){
        workersList.add(worker);
        return this;
    }

    public ShellParser removeWorker(ParserWorker worker){
        workersList.remove(worker);
        return this;
    }

    public void deactivate(){
        isActive = false;
        executorService.shutdown();
        parseTread.interrupt();
        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("ForsinG!");
            executorService.shutdownNow();
        }
        parseTread.interrupt();
    }

    public void waitFor() throws InterruptedException {
        synchronized (workerPoolTerminatedMonitor){
            while (!workerPoolTerminated){
                workerPoolTerminatedMonitor.wait();
            }
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
