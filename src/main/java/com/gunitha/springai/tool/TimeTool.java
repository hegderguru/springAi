package com.gunitha.springai.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class TimeTool {

    @Tool(name = "currentLocalDateTimeInString", description = "Get the current Local date time")
    public String time() {
        log.info("Local Time now is {}", LocalDateTime.now());
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Tool(name = "currentDateTimeInString", description = "Get the current date time for specific timezone")
    public String timeInTimeZone(@ToolParam(description = "Value that represents the timezone") String timezone) {
        log.info("Time now is {}", LocalDateTime.now(ZoneId.of(timezone)));
        return LocalDateTime.now(ZoneId.of(timezone)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
