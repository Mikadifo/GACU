package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;

import java.util.Optional;
import java.util.function.Function;

public interface UserValidator extends Function<UserDB, Optional<String>> {

    static UserValidator userNameIsValid() {
	return user -> (user.getUsername().isEmpty()) ? Optional.of("EL NOMBRE ESTA VACIO") : Optional.empty();
    }

    default UserValidator and(UserValidator other) {
        return user -> {
            Optional<String> result = this.apply(user);

            return result.isPresent() ? result : other.apply(user);
        };
    }

}
