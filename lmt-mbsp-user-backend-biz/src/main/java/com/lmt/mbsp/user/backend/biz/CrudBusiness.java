package com.lmt.mbsp.user.backend.biz;

import org.springframework.beans.BeanUtils;

/*
 * @描述：
 * @作者：Tangsm
 * @创建时间：2018-07-09 11:28:22
 */
public interface CrudBusiness<E, M> {
    default E modelToEntity(M model, E entity) {
        BeanUtils.copyProperties(model, entity);
        return entity;
    }
    default M entityToModel(E entity, M model) {
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
