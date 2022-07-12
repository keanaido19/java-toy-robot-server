package za.co.wethinkcode.robotworlds.cli.arguments;

import org.apache.commons.cli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
    public Object getArgumentValue(
            CommandLine cmd
    ) throws IllegalArgumentException {
        List<int[]> obstaclePositions = new ArrayList<>();

        String value = (String) super.getArgumentValue(cmd);

        if (null == value || "none".equals(value)) return obstaclePositions;

        Pattern pattern = Pattern.compile("^-?\\d+,-?\\d+$");
        if (!pattern.matcher(value).find())
            throw new IllegalArgumentException(
                    "Position of fixed obstacle [x,y] must be in the " +
                    "form 'x,y', or 'none'"
            );

        String[] coordinates = value.split(",");

        try {
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            obstaclePositions.add(new int[] {x, y});
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Position of fixed obstacle as [x,y] coordinate in form " +
                    "'x,y' must be integers!"
            );
        }

        return obstaclePositions;
    }
}
