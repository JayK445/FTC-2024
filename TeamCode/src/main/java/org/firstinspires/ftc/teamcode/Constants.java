package org.firstinspires.ftc.teamcode;

public final class Constants {

    public static final class Config {
        //enable when testing, just havent figured out implementing ftc dashboard yet
        public static final boolean SHOW_DEBUG_DATA = false;
    }

    public static final class Elevator {

        //FIXME Placeholder
        public static final double ELEVATOR_SLANT_ANGLE = 36;

        public static final double GRAVITY_OFFSET_PERCENT = 0.03;

        public static final class Setpoints {
            //FIXME placeholder values
            public static final double MIN_EXTENSION_INCHES = 0;
            public static final double MAX_EXTENSION_INCHES = 28.75;
            public static final double MID_HEIGHT_INCHES = 6;
            public static final double HIGH_SETPOINT = 10;
        }
    }

    public static final class Intake {


        public static final class ModePowers {
            //FIXME placeholder values
            public static final double OFF = 0;
            public static final double INTAKE = 0.3;

            public static final double OUTTAKE = -0.3;
        }
    }

    public static final class Shooter {

    }

}
