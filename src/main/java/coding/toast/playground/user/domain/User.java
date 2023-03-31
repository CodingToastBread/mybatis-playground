package coding.toast.playground.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Alias("User")
@Getter @Setter @ToString
public class User {
    private Long id;

    private String name;

    private String phoneNumber;
}
