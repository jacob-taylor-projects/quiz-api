package com.cooksys.quiz_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -4711950716865008179L;

    private String message;
}
