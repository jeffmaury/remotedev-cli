package com.jeffmaury;

import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.eclipse.jkube.kit.common.util.Slf4jKitLogger;
import org.eclipse.jkube.kit.remotedev.RemoteDevelopmentConfig;
import org.eclipse.jkube.kit.remotedev.RemoteDevelopmentService;
import org.eclipse.jkube.kit.remotedev.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.ExecutionException;

@Command(name = "remote", mixinStandardHelpOptions = true)
public class RemoteCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCommand.class);

    @Parameters(paramLabel = "<service name>", description = "The remote service to bind.")
    String serviceName;

    @Parameters(paramLabel = "<service port>", description = "The remote service port to bind.")
    int remotePort;

    @Parameters(paramLabel = "<localPort>", defaultValue = "-1", description = "The local port to bind the service to.", )
    int localPort;

    @Override
    public void run() {
        var remoteService = RemoteService.builder()
                .hostname(serviceName)
                .port(remotePort)
                .localPort(localPort)
                .build();
        var client = new KubernetesClientBuilder().build();
        var config = RemoteDevelopmentConfig.builder().remoteService(remoteService).build();
        var logger = new Slf4jKitLogger(LOGGER);
        var service = new RemoteDevelopmentService(logger, client,
                config);
        try {
            service.start().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
