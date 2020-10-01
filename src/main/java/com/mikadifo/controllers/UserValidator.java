package com.mikadifo.controllers;

import com.mikadifo.models.table_statements.UserDB;

import java.util.Optional;
import java.util.function.Function;

public interface UserValidator extends Function<UserDB, Optional<String>> {

    static UserValidator isUsernameValid() {
	return user -> (user.getUsername().matches("^[A-Za-z]\\w{4,48}[A-Za-z\\d]$")) ?
	    Optional.empty() : Optional.of("El nombre de usuario debe contener solo (letras,numeros,_)");
    }

    static UserValidator isPasswordValid() {
	return user -> (user.getPassword().matches(".*\\d&[A-Z].*{8,128}$")) ?
	    Optional.empty() : Optional.of("La contrasena debe tener minimo (un numero y una letra mayuscula)") ;
    }

    static UserValidator isCitySelected() {
	return user -> (user.getCityId() != 0) ?
	    Optional.empty() : Optional.of("Debe Seleccionar una ciudad");
    }

    static UserValidator isLoginValid() {
	return user -> (loginValidation(user.getLogin())) ?
	    Optional.empty() : Optional.of("La cedula no es real");
    }

    static boolean loginValidation(String login) {
	final int MAX_DIGITS = 10;
        int digits[] = new int[MAX_DIGITS];
        int checkerDigit = 0;

        if (login.length() != MAX_DIGITS) {
            return false;
        }

        for (int i = 0; i < MAX_DIGITS; i++) {
            digits[i] = Integer.parseInt("" + login.charAt(i));
            if ((i + 1) < MAX_DIGITS) {
                if ((i + 1) % 2 != 0) {
                    digits[i] *= 2;
                    if (digits[i] > 9) {
                        digits[i] -= 9;
                    }
                }
                checkerDigit += digits[i];
            } else {
                break;
            }
        }

        checkerDigit %= 10;

        if (checkerDigit != 0) {
            checkerDigit = 10 - checkerDigit;
        }

        return checkerDigit == digits[9];
    }


    default UserValidator and(UserValidator other) {
        return user -> {
            Optional<String> result = this.apply(user);

            return result.isPresent() ? result : other.apply(user);
        };
    }

}
