package coding.toast.playground.sql_injection.service;

import coding.toast.playground.sql_injection.dto.SqlInjectionTestDTO;
import coding.toast.playground.sql_injection.mapper.SqlInjectionTestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SqlInjectionService {

    private final SqlInjectionTestMapper sqlInjectionTestMapper;

    public List<SqlInjectionTestDTO> listOfItem(SqlInjectionTestDTO dto) {
        return sqlInjectionTestMapper.selectList(dto);
    }

    public SqlInjectionTestDTO item(SqlInjectionTestDTO dto) {
        return sqlInjectionTestMapper.select(dto);
    }

    public int createItem(SqlInjectionTestDTO dto) {
        return sqlInjectionTestMapper.insert(dto);
    }
}
