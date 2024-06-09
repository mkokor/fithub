package com.fithub.services.chat.core.context;

import com.fithub.services.chat.dao.model.UserEntity;

import lombok.Data;

@Data
public class UserContext {

    private static final ThreadLocal<UserContext> CONTEXT = ThreadLocal.withInitial(UserContext::new);

    private UserEntity user;

    public static UserContext getCurrentContext() {
        return CONTEXT.get();
    }

    public static void setCurrentContext(UserContext context) {
        CONTEXT.set(context);
    }

    public static void clear() {
        CONTEXT.remove();
    }

}