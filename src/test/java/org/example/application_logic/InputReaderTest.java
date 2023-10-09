package org.example.application_logic;

import org.example.application_logic.In.InputReader;
import org.example.application_logic.Repository.OperationData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReaderTest {

    @BeforeEach
    public

    @Test
    void beginDialog() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("1/".getBytes());
        InputStream inputStream = System.in;  // сохраняем ссылку на ввод с клавиатуры
        System.setIn(byteArrayInputStream);
        Assertions.assertThrows(InputMismatchException.class, InputReader::beginDialog);
        System.setIn(inputStream);
    }

}
