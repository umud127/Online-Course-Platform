package az.the_best.onlinecourseplatform.controller.impl;

import az.the_best.onlinecourseplatform.controller.IRestBaseController;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.exception.MessageType;

public class RestBaseControllerIMPL implements IRestBaseController {

    @Override
    public <T> BaseEntity<T> ok(T data) {
        return BaseEntity.ok(data);
    }

    @Override
    public <T> BaseEntity<T> notOk(MessageType messageType, String id) {
        return BaseEntity.notOk(messageType,id);
    }
}
