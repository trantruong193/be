package com.shopme.be.persistant.dto;

import com.shopme.be.persistant.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String sender;
    private String receiver;
    private String message;
    private String date;
    private Status status;
}
