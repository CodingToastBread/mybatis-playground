package coding.toast.playground.sql_injection.mapper;

import coding.toast.playground.sql_injection.dto.SqlInjectionTestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SqlInjectionTestMapper {

    List<SqlInjectionTestDTO> selectList(SqlInjectionTestDTO dto);

    SqlInjectionTestDTO select(SqlInjectionTestDTO dto);

    int insert(SqlInjectionTestDTO dto);
}
