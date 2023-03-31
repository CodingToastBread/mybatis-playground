package coding.toast.playground.batch.service;

import coding.toast.playground.batch.DDLBatchMapper;
import coding.toast.playground.batch.dto.ExcelLikeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DDLService {


    private final JdbcTemplate jdbcTemplate;

    private final DDLBatchMapper ddlMapper;


    public void createTable(ExcelLikeDTO map) {

        ddlMapper.createTableBatch(map);
        final int headerWidth = map.getHeaderColumnList().size();

        String insertHeaderColString = map.getHeaderColumnList()
                .stream().map(s -> "\"" + s + "\"").reduce((s, s2) -> s + ", " + s2)
                .orElseThrow(() -> new RuntimeException("your header is weird..."));

        System.out.println("insertHeaderColString = " + insertHeaderColString);
        // insertHeaderColString = "id", "attr1", "attr2", "attr3", "attr4"

        String preparedQuestionMark = getQuestionMark(headerWidth);

        String jdbcBatchQuerySqlString = """
                insert into "%s"."%s" (%s)
                values (%s)
                """.formatted(
                map.getSchemaName(),
                map.getTableName(),
                insertHeaderColString,
                preparedQuestionMark
        );

        System.out.println("jdbcBatchQuerySqlString = \n" + jdbcBatchQuerySqlString);

        List<List<Object>> bodyColumnList = map.getBodyColumnList();

        List<Object[]> list = bodyColumnList.stream()
                .map(List::toArray).toList();

        for (Object[] objects : list) {
            System.out.println("Arrays.toString(objects) = " + Arrays.toString(objects));
        }

        jdbcTemplate.batchUpdate(jdbcBatchQuerySqlString, list);
    }

    public void createTableWithMyBatis(ExcelLikeDTO map) {

    }


    private String getQuestionMark(int headerWidth) {
        String[] strings = new String[headerWidth];
        Arrays.fill(strings, "?");
        return String.join(",", strings);
    }

}
