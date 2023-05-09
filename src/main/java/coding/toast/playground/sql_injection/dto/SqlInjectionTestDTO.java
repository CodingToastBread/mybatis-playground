package coding.toast.playground.sql_injection.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("SqlInjectionTestDTO")
public class SqlInjectionTestDTO {
    private Long id;
    private String name;
    private Integer age;
}
