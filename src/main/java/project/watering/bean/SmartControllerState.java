package project.watering.bean;

import org.springframework.stereotype.Component;

@Component
public class SmartControllerState {
    private boolean enabled = false;
    private boolean pumpEnabled = false;
    private boolean lightEnabled = false;

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

}
