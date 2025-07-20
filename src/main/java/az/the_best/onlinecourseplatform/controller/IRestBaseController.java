package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.exception.MessageType;

public interface IRestBaseController {

    public <T> BaseEntity<T> ok(T data);

    public <T> BaseEntity<T> notOk(MessageType messageType, String id);
}
