package za.co.wethinkcode.robotworlds.CLIhandler.arguments;

import org.apache.commons.cli.CommandLine;

import java.util.ArrayList;

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
        return new ArrayList<>();
    }
}
