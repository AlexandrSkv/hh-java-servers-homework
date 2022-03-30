package Dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "date", "value" })
public class DtoGetCounter {

    private int value;
    private String date;

    public DtoGetCounter(int value){
        this.value = value;
        date = java.time.Instant.now().toString();
    }

    public String getDate() {
        return date = java.time.Instant.now().toString();
    }

    public int getValue() {
        return value;
    }
}
