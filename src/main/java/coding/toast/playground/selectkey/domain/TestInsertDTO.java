package coding.toast.playground.selectkey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("TestInsertDTO")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TestInsertDTO {
    private Long id;

    private String name;
}
