package coding.toast.playground.nulltest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("NullTestDTO")
public class NullTestDTO {
	private Long id;
	private String name;
	private Integer age;
}
