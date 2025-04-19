package org.mutsenko.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class User {

    private final Long id;
    private final String username;
}
