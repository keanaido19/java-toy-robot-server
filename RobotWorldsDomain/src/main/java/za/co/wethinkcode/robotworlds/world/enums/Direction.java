package za.co.wethinkcode.robotworlds.world.enums;

public enum Direction {
    NORTH {
        @Override
        public Direction getRightDirection() {
            return EAST;
        }

        @Override
        public Direction getLeftDirection() {
            return WEST;
        }
    },
    EAST {
        @Override
        public Direction getRightDirection() {
            return SOUTH;
        }

        @Override
        public Direction getLeftDirection() {
            return NORTH;
        }
    },
    SOUTH {
        @Override
        public Direction getRightDirection() {
            return WEST;
        }

        @Override
        public Direction getLeftDirection() {
            return EAST;
        }
    },
    WEST {
        @Override
        public Direction getRightDirection() {
            return NORTH;
        }

        @Override
        public Direction getLeftDirection() {
            return SOUTH;
        }
    };

    public abstract Direction getRightDirection();

    public abstract Direction getLeftDirection();
}
