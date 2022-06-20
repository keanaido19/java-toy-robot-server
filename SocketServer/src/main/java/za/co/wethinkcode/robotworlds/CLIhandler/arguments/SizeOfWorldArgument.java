package za.co.wethinkcode.robotworlds.CLIhandler.arguments;

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
    public Object getArgumentValue(
            CommandLine cmd
    ) throws IllegalArgumentException {

        String value = (String) super.getArgumentValue(cmd);

        if (null == value) return 1;

        try {
            int sizeOfWorld = Integer.parseInt(value);
            if (1 <= sizeOfWorld && sizeOfWorld <= 9999) return sizeOfWorld;
            throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Size of the world as one side of a square grid must " +
                            "be an integer from 1-9999"
            );
        }
    }
}
