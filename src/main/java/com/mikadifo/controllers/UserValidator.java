package com.mikadifo.controllers;

import static com.mikadifo.controllers.UserValidator.*;
import static com.mikadifo.controllers.UserValidator.ValidationResult.*;

import java.util.function.Function;

public interface UserValidator extends Function<User, ValidationResult> {

    default UserValidator and(UserValidator other) {
        return user -> {
            ValidationResult result = this.apply(user);

            return result.equals(SUCCESS) ? other.apply(user) : result;
        };
    }

    enum ValidationResult {
        SUCCESS,
        LA_CEDULA_INGRESADA_ES_INCORRECTA,
        LA_CEDULA_INGRESADA_NO_SE_ENCUENTRA_EN_LA_BASE_DE_DATOS,
        LA_CONTRASENA_NO_ES_VALIDA,
        LA_CONTRASEÑA_NO_COINCIDE_CON_LA_CEDULA,
        EL_NOMBRE_DE_USUARIO_ES_INCORRECTO,
    }

}