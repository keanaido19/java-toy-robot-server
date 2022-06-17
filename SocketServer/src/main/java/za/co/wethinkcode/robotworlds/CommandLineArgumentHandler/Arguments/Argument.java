package za.co.wethinkcode.robotworlds.CommandLineArgumentHandler.Arguments;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public abstract class Argument extends Option {
    public Argument(
            String option,
            String longOption,
            boolean hasArg,
            String description
    ) throws IllegalArgumentException {
        super(option, longOption, hasArg, description);
    }

    public Object getArgumentValue(CommandLine cmd) {
        return cmd.getOptionValue(super.getLongOpt());
    }
}
