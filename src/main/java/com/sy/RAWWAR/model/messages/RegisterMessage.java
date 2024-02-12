package com.sy.RAWWAR.model.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterMessage {
    private final String kitId;
    private final String passcode;
    private final String missioId;
}
