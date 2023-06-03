package ru.netology.mycloudstorage.modele;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Developer {
    private Integer id;
    private String login;
    private String password;
}
