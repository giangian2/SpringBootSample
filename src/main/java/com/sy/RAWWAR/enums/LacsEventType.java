package com.sy.RAWWAR.enums;

import java.util.Optional;

public enum LacsEventType {
    MISSION_STATUS,
    KIT_STATUS,
    HIT_EVENT,
    REVIVE_EVENT;

    public Optional<String> toString(LacsEventType type) {
        switch (type) {
            case MISSION_STATUS:
                return Optional.of("missionStatusEvent");

            case KIT_STATUS:
                return Optional.of("kitStatusEvent");

            case HIT_EVENT:
                return Optional.of("hitEvent");

            case REVIVE_EVENT:
                return Optional.of("reviveEvent");

            default:
                return Optional.empty();
        }
    }

    public Optional<LacsEventType> fromString(String string) {
        switch (string) {
            case "missionStatusEvent":
                return Optional.of(MISSION_STATUS);

            case "hitEvent":
                return Optional.of(HIT_EVENT);

            case "kitStatusEvent":
                return Optional.of(KIT_STATUS);

            case "reviveEvent":
                return Optional.of(REVIVE_EVENT);

            default:
                return Optional.empty();

        }
    }
}
