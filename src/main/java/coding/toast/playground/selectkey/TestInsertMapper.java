package coding.toast.playground.selectkey;

import coding.toast.playground.selectkey.domain.TestInsertDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface TestInsertMapper {

	int insertWithSelectKey(TestInsertDTO dto);
}
