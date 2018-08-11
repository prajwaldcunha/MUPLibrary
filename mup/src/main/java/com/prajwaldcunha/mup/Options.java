package com.prajwaldcunha.mup;

public class Options {


    String folderName ;
    float compressionRate;
    private boolean isNotificationEnabled;
    private int notificationIcon;
    private int notificationColor;


    public Options() {
        folderName = "/folder";
        compressionRate = 20;
        isNotificationEnabled = false;
        notificationIcon = R.drawable.baseline_notification_important_black_24;
        notificationColor = R.color.colorPrimary;
    }

    public void enableNotification(boolean enabled) {

        isNotificationEnabled = enabled;
    }

    public void setNotificationOptions(int icon, int color) {
        this.notificationIcon = icon;
        this.notificationColor = color;
    }

    public void setCompressionRate(int compressionRate) {
        this.compressionRate = compressionRate;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    protected String getFolderName() {
        return folderName;
    }



    protected float getCompressionRate() {
        return compressionRate;
    }



    protected boolean isNotifyEnabled() {
        return isNotificationEnabled;
    }

    protected int getNotificationIcon() {
        return notificationIcon;
    }

    protected int getNotificationColor() {
        return notificationColor;
    }
}
