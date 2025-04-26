package project.watering.bean;

import org.springframework.stereotype.Component;

@Component
public class SmartControllerState {
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
