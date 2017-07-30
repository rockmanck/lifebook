package ua.lifebook.reminders;

import java.time.LocalDateTime;

public enum RemindBefore {
    TEN_MINUTES {
        @Override public LocalDateTime getRemindTime(LocalDateTime planTime) {
            return planTime.minusMinutes(10);
        }
    },
    HALF_AN_HOUR {
        @Override public LocalDateTime getRemindTime(LocalDateTime planTime) {
            return planTime.minusMinutes(30);
        }
    },
    HOUR {
        @Override public LocalDateTime getRemindTime(LocalDateTime planTime) {
            return planTime.minusHours(1);
        }
    },
    DAY_BEFORE {
        @Override public LocalDateTime getRemindTime(LocalDateTime planTime) {
            return planTime.minusDays(1);
        }
    };

    public abstract LocalDateTime getRemindTime(LocalDateTime planTime);
}
