package com.mikadifo.controllers;

import static com.mikadifo.controllers.UserValidator.*;
import static com.mikadifo.controllers.UserValidator.ValidationResult.*;
import static com.mikadifo.controllers.UserLoginValidator.*;

import java.util.function.Function;

public interface UserLoginValidator extends UserValidator {

    static UserLoginValidator isUsernameValid() {
        return user -> user.getUsername().matches("^[A-Za-z]\\w{4,48}[A-Za-z\\d]$") ?
            SUCCESS : EL_NOMBRE_DE_USUARIO_ES_INCORRECTO;
    }

}