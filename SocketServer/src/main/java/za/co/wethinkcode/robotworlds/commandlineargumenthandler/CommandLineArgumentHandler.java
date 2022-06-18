package za.co.wethinkcode.robotworlds.commandlineargumenthandler;


import org.apache.commons.cli.*;
import za.co.wethinkcode.robotworlds.commandlineargumenthandler.arguments.Argument;
import za.co.wethinkcode.robotworlds.commandlineargumenthandler.arguments.ObstacleArgument;
import za.co.wethinkcode.robotworlds.commandlineargumenthandler.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.commandlineargumenthandler.arguments.SizeOfWorldArgument;

import java.util.Arrays;
import java.util.List;

public class CommandLineArgumentHandler {
    private static final List<Argument> arguments = Arrays.asList(
            new ServerPortArgument(),
            new ObstacleArgument(),
            new SizeOfWorldArgument()
    );

    private final Options options = new Options();

    private CommandLine cmd;

    public CommandLineArgumentHandler(String[] args) {
        for (Option option : arguments) {
            options.addOption(option);
        }
        this.setCommandLine(args);
    }

    private String getUsageMessage() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("robots ");

        for (Option option : arguments) {
            stringBuilder
                    .append("[-")
                    .append(option.getOpt())
                    .append("=<")
                    .append(option.getArgName())
                    .append(">] ")
            ;
        }
        return stringBuilder.toString();
    }

    private void setCommandLine(String[] args) {
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(
                    getUsageMessage(),
                    "Starts the Robot World server",
                    options,
                    ""
            );
            System.exit(1);
        }
    }

    public Object getArgumentValue(
            Argument argument
    ) throws IllegalArgumentException {

        if (arguments.contains(argument)) {
            return argument.getArgumentValue(cmd);
        }

        throw new IllegalArgumentException(
                "Argument not defined in command line options!"
        );
    }
}
