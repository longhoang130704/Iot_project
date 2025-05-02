package project.watering.bean;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class SmartControllerState {
    private boolean enabled = false;
    private boolean pumpEnabled = false;
    private boolean lightEnabled = false;
    private LocalTime startTime;
    private LocalTime endTime;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabledPump() {
        return pumpEnabled;
    }

    public void setEnabledPump(boolean pumpEnabled) {
        this.pumpEnabled = pumpEnabled;
    }

    public boolean isEnabledLight() {
        return lightEnabled;
    }

    public void setEnabledLight(boolean lightEnabled) {
        this.lightEnabled = lightEnabled;
    }

    public void setStartTime(LocalTime starTime) {
        this.startTime = starTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }
}
