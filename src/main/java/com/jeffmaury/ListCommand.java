package com.jeffmaury;

import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.KubernetesClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

@Command(name = "list", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalCommand.class);

    @Override
    public void run() {
        var client = new KubernetesClientBuilder().build();
        try {
            client.services().list().getItems().forEach(service -> System.out.println("service:" + service.getMetadata().getName()));
        } catch (KubernetesClientException e) {
            System.exit(-1);
        }
    }
}
