package za.co.wethinkcode.robotworlds.CommandLineArgumentHandler.Arguments;

import org.apache.commons.cli.CommandLine;

public class ServerPortArgument extends Argument {
    public ServerPortArgument() {
        super(
                "p",
                "port",
                true,
                "Port to listen for client connections"
        );
        super.setArgName("port");
        super.setRequired(false);
    }

    @Override
    public Object getArgumentValue(
            CommandLine cmd
    ) throws IllegalArgumentException {

        String value = (String) super.getArgumentValue(cmd);

        if (null == value) return 5000;

        try {
            int portNumber = Integer.parseInt(value);
            if (0 <= portNumber && portNumber <= 9999) return portNumber;
            throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    " Port to listen for client connections must be an " +
                            "integer from 0-9999"
            );
        }
    }
}
