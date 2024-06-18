package com.cooksys.quiz_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -6354420992104990897L;

    private String message;
}
