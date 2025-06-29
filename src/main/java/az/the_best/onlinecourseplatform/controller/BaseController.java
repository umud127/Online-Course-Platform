package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.controller.impl.IBaseController;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.exception.MessageType;

public class BaseController implements IBaseController {

    @Override
    public <T> BaseEntity<T> ok(T data) {
        return BaseEntity.ok(data);
    }

    @Override
    public <T> BaseEntity<T> notOk(MessageType messageType, String id) {
        return BaseEntity.notOk(messageType,id);
    }
}
