package itsu.java.yournote.model.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class RegisterForm implements Serializable {
    @NotEmpty(message = "{mail}")
    @Email
    private String mail;

    @NotEmpty(message = "{password}")
    @Size(min = 8)
    private String password;

    @NotEmpty(message = "{passwordConfirm}")
    @Size(min = 8)
    private String passwordConfirm;

    @NotEmpty(message = "{username}")
    @Size(min = 1, max = 20)
    private String username;

    private boolean allowFormula;
}
