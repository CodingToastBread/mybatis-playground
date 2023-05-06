package coding.toast.playground.nulltest;

import coding.toast.playground.nulltest.dto.NullTestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NullTestMapper {
	
	NullTestDTO findWithParamType(Long id);
	
	NullTestDTO findWithOutParamType(Long id);
	
	NullTestDTO findWithOutParamTypeButHasJdbcType(Long id);
	
	NullTestDTO findWithParamTypeString(String name);
	
	NullTestDTO findWithOutParamTypeString(String name);
	
	NullTestDTO findWithOutParamTypeButHasJdbcTypeString(String name);
	
	int insert(NullTestDTO dto);

}
