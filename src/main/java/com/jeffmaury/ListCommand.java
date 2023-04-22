package com.jeffmaury;

import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.client.OpenShiftClient;
import org.eclipse.jkube.kit.common.util.Slf4jKitLogger;
import org.eclipse.jkube.kit.remotedev.LocalService;
import org.eclipse.jkube.kit.remotedev.RemoteDevelopmentConfig;
import org.eclipse.jkube.kit.remotedev.RemoteDevelopmentService;
import org.eclipse.jkube.kit.remotedev.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.ExecutionException;

@Command(name = "list", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalCommand.class);

    @Override
    public void run() {
        var client = new KubernetesClientBuilder().build();
        try {
            var openshiftClient = client.adapt(OpenShiftClient.class);
            openshiftClient.projects().list().getItems().forEach(project -> System.out.println("ns:" + project.getMetadata().getName()));
        } catch (KubernetesClientException e) {
            try {
                client.namespaces().list().getItems().forEach(ns -> System.out.println("ns:" + ns.getMetadata().getName()));
            } catch (KubernetesClientException e1) {
                System.exit(-1);
            }
        }
    }
}
