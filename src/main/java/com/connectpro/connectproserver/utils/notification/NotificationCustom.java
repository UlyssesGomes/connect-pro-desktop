package com.connectpro.connectproserver.utils.notification;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationCustom {

    private NotificationType statusNotification = NotificationType.SUCCESS;
    private int thresholdNumberNotification;
    private int durationSeconds;
    private NotificationType notificationType;
    private Pos position;

    private Notifications notificationBuilder;

    public NotificationCustom() {
        thresholdNumberNotification = 10;
        durationSeconds = 5;
        notificationType = NotificationType.DEFAULT;
        position = Pos.BOTTOM_RIGHT;
    }

    public void setNotificationProperties(int thresholdNumberNotification, int durationSeconds, Pos position) {
        this.thresholdNumberNotification = thresholdNumberNotification;
        this.durationSeconds = durationSeconds;
        this.position = position;
    }

    public void showNotification(String title, String message) {
        showNotificationType(title, message, this.notificationType);
    }

    public void showNotificationType(String title, String message, NotificationType notificationType){
        notificationBuilder = Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(durationSeconds))
                .position(position)
                .threshold( thresholdNumberNotification,
                        Notifications.create().title("Threshold Notification"));

        showNotificationType(notificationType);
    }

    private void showNotificationType(NotificationType type){
        ImageView icon;
        switch (type) {
            case NotificationType.SUCCESS:
                notificationBuilder.styleClass("notification-success");
                icon = new ImageView(getClass().getResource("/images/icons/check-circle.png").toString());
                icon.setFitHeight(35d);
                icon.setFitWidth(35d);
                notificationBuilder.graphic(icon);
                notificationBuilder.show();
                break;
            case NotificationType.INFO:
                notificationBuilder.styleClass("notification-info");
                icon = new ImageView(getClass().getResource("/images/icons/info.png").toString());
                icon.setFitHeight(35d);
                icon.setFitWidth(35d);
                notificationBuilder.graphic(icon);
                notificationBuilder.show();
                break;
            case NotificationType.ERROR:
                notificationBuilder.styleClass("notification-error");
                icon = new ImageView(getClass().getResource("/images/icons/x-circle.png").toString());
                icon.setFitHeight(35d);
                icon.setFitWidth(35d);
                notificationBuilder.graphic(icon);
                notificationBuilder.show();
                break;
            case NotificationType.WARNING:
                notificationBuilder.styleClass("notification-warning");
                icon = new ImageView(getClass().getResource("/images/icons/alert-triangle.png").toString());
                icon.setFitHeight(35d);
                icon.setFitWidth(35d);
                notificationBuilder.graphic(icon);
                notificationBuilder.show();
                break;
            case NotificationType.CONFIRM:
                notificationBuilder.styleClass("notification-confirm");
                icon = new ImageView(getClass().getResource("/images/icons/help-circle.png").toString());
                icon.setFitHeight(35d);
                icon.setFitWidth(35d);
                notificationBuilder.graphic(icon);
                notificationBuilder.show();
                break;
            default:
                notificationBuilder.show();
        }
    }

    public void setAction(EventHandler<ActionEvent> onAction) {
        notificationBuilder.onAction(onAction);
    }
}
