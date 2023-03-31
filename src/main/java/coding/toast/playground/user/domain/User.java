package coding.toast.playground.user.domain;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Alias("User")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class User {
    private Long id;

    private String name;

    private String phoneNumber;
}
