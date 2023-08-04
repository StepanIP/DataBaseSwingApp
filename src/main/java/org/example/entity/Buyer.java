package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Buyer {
    private int id;
    private String name;
    private String email;
    private String phone_number;

    public Buyer(int id){
        this.id=id;
    }



}
