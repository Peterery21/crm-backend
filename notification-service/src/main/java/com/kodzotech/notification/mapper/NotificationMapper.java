package com.kodzotech.notification.mapper;

import com.kodzotech.notification.model.Notification;
import com.kodzotech.notification.dto.NotificationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    public Notification dtoToEntity(NotificationDto notificationDto);

}
