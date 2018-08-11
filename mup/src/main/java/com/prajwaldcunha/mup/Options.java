package com.prajwaldcunha.mup;

public class Options {

    private static Options instance;
    String folderName = "/folder";
    float compressionRate = 20;
    private boolean isNotificationEnabled = false;
    private int notificationIcon;
    private int notificationColor;

    public static Options init() {
        if (instance == null) {
            instance = new Options();
        }
        return instance;
    }

    public static Options getInstance() {
        return instance;
    }

   public void enableNotification(boolean enabled) {

        isNotificationEnabled = enabled;
    }

    public void setNotificationOptions(int icon, int color) {
        this.notificationIcon = icon;
        this.notificationColor = color;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public void setCompressionRate(int compressionRate) {
        this.compressionRate = compressionRate;
    }


    public String getFolderName() {
        return folderName;
    }

    public float getCompressionRate() {
        return compressionRate;
    }

    public boolean isNotifyEnabled() {
        return isNotificationEnabled;
    }

    public int getNotificationIcon() {
        return notificationIcon;
    }

    public int getNotificationColor() {
        return notificationColor;
    }
}
