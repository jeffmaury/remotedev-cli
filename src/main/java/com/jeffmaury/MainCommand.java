package com.jeffmaury;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;

@TopCommand
@Command(mixinStandardHelpOptions = true, subcommands = { LocalCommand.class, RemoteCommand.class })
public class MainCommand {
}
