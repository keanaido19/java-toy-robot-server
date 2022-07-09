package za.co.wethinkcode.robotworlds.world.enums;

public enum UpdateResponse {
    SUCCESS {
        @Override
        public String getMessage() {
            return "Done";
        }
    },
    FAILED_OUTSIDE_WORLD {
        @Override
        public String getMessage() {
            return "Sorry, I cannot go outside my safe zone.";
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
