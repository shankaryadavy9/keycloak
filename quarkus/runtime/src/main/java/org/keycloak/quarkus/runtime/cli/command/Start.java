/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.quarkus.runtime.cli.command;

import static org.keycloak.quarkus.runtime.cli.command.AbstractStartCommand.OPTIMIZED_BUILD_OPTION_LONG;

import org.keycloak.quarkus.runtime.Environment;
import org.keycloak.quarkus.runtime.Messages;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = Start.NAME,
        header = "Start the server.",
        description = {
                "%nUse this command to run the server in production."
        },
        footer = "%nBy default, this command tries to update the server configuration by running a '" + Build.NAME + "' before starting the server. You can disable this behavior by using the '" + OPTIMIZED_BUILD_OPTION_LONG + "' option:%n%n"
                + "      $ ${PARENT-COMMAND-FULL-NAME:-$PARENTCOMMAND} ${COMMAND-NAME} '" + OPTIMIZED_BUILD_OPTION_LONG + "'%n%n"
                + "By doing that, the server should start faster based on any previous configuration you have set when manually running the '" + Build.NAME + "' command.")
public final class Start extends AbstractStartCommand implements Runnable {

    public static final String NAME = "start";

    @CommandLine.Mixin
    OptimizedMixin optimizedMixin;

    @CommandLine.Mixin
    ImportRealmMixin importRealmMixin;

    @CommandLine.Mixin
    HelpAllMixin helpAllMixin;

    @Override
    protected void doBeforeRun() {
        Environment.updateProfile(true);
        if (Environment.isDevProfile()) {
            executionError(spec.commandLine(), Messages.devProfileNotAllowedError(NAME));
        }
    }

    @Override
    public boolean includeRuntime() {
        return true;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
