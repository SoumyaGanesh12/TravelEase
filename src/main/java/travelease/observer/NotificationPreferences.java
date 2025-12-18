package travelease.observer;

public class NotificationPreferences {
    private final boolean email;
    private final boolean sms;
    private final boolean app;

    public NotificationPreferences(boolean email, boolean sms, boolean app) {
        this.email = email;
        this.sms = sms;
        this.app = app;
    }

    public boolean isEmailEnabled() { return email; }
    public boolean isSmsEnabled() { return sms; }
    public boolean isAppEnabled() { return app; }
}
