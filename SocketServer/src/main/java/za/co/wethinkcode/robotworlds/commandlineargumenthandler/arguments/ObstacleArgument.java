package za.co.wethinkcode.robotworlds.commandlineargumenthandler.arguments;

import org.apache.commons.cli.CommandLine;

public class ObstacleArgument extends Argument{
    public ObstacleArgument() {
        super(
                "o",
                "obstacle",
                true,
                "Position of fixed obstacle as [x,y] coordinate in " +
                        "form 'x,y', or 'none'"
        );
        super.setArgName("obstacle");
        super.setRequired(false);
    }

    @Override
    public Object getArgumentValue(CommandLine cmd) {
        return null;
    }
}
