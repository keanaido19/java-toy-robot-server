package za.co.wethinkcode.robotworlds.CommandLineArgumentHandler.Arguments;

import org.apache.commons.cli.CommandLine;

public class SizeOfWorldArgument extends Argument {
    public SizeOfWorldArgument() {
        super(
                "s",
                "size",
                true,
                "Size of the world as one side of a square grid"
        );
        super.setArgName("size");
        super.setRequired(false);
    }

    @Override
    public Object getArgumentValue(CommandLine cmd) {
        return null;
    }
}
