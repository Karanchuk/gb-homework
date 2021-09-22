package ru.gb.springSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.springSecurity.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String username;
    private Integer score;

    public static UserDto valueOf(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getScore()
        );
    }
}
