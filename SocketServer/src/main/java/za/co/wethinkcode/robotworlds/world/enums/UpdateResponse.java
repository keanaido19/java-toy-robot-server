package za.co.wethinkcode.robotworlds.world.enums;

public enum UpdateResponse {
    SUCCESS {
        @Override
        public String getMessage() {
            return "Done";
        }
    },
    FAILED_NOT_IN_WORLD {
        @Override
        public String getMessage() {
            return "Object not in world";
        }
    },
    FAILED_OBSTRUCTED {
        @Override
        public String getMessage() {
            return "Obstructed";
        }
    };

    public abstract String getMessage();
}
