package com.example.demo.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TransactionUtils {
    public static Long toMidnightTimeStamp(Long timestampInMillis) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampInMillis), ZoneId.of("UTC"));
        LocalDateTime midnight = dateTime.toLocalDate().atStartOfDay();
        return midnight.toEpochSecond(ZoneOffset.UTC) * 1000; // 자정 타임스탬프를 밀리초 단위로 변환
    }
}

