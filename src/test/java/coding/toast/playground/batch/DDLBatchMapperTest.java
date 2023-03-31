package coding.toast.playground.batch;

import coding.toast.playground.batch.dto.ExcelLikeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DDLBatchMapper.xml
 */
@SpringBootTest
class DDLBatchMapperTest {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @Autowired
    private DDLBatchMapper ddlMapper;



    @Test
    @Transactional // by the way, postgresql supports ddl rollback!!! amazing!
    // @Commit
    @DisplayName("with JdbcTemplate")
    void createTableWithJdbcTest()  {

        transactionTemplate.executeWithoutResult(transactionStatus -> {
            ExcelLikeDTO map = ExcelLikeDTO.builder()
                    .schemaName("coding_toast")
                    .tableName("user_test")
                    .headerColumnList(List.of("id", "attr1", "attr2", "attr3", "attr4"))
                    .bodyColumnList(List.of(
                            List.of("New York", 1L, 2L, 3L, 4L),
                            List.of("California", 2L, 3L, 4L, 5L),
                            List.of("Texas", 0L, 0L, 0L, 0L),
                            List.of("Arizona", 99L, 23L, 12L, 51L),
                            List.of("Indiana", 41L, 12L, 0L, 99L),
                            List.of("Washington", 511L, 232L, 32L, 41L),
                            List.of("Oklahoma", 11L, 22L, 33L, 45L)
                    ))
                    .build();


            // map.getHeaderColumnList().stream().skip(1).map(s -> {
            //     return "%s int";
            // }).reduce((s, s2) -> s + ", " + s2)

            String tableCreateString = """
                    create table "%s"."%s" (
                                    
                    )
                    """;
            List<String> tableCreateStrList = new ArrayList<String>();
            tableCreateStrList.add(map.getSchemaName());
            tableCreateStrList.add(map.getTableName());
            tableCreateStrList.addAll(map.getHeaderColumnList());
            jdbcTemplate.execute("""
                            create table "%s"."%s" (
                                "%s" character varying(20) ,
                                "%s" int ,
                                "%s" int ,
                                "%s" int ,
                                "%s" int
                    )
                    """.formatted(
                    tableCreateStrList.toArray()
            ));


            // ddlMapper.createTableBatch(map);
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
        });

    }

    private static String getQuestionMark(int headerWidth) {
        String[] strings = new String[headerWidth];
        Arrays.fill(strings, "?");
        return String.join(",", strings);
    }


    @Test
    @Transactional
    // @Commit
    @DisplayName("with only mybatis")
    void createTableTest2() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            ExcelLikeDTO map = ExcelLikeDTO.builder()
                    .schemaName("coding_toast")
                    .tableName("user_test")
                    .headerColumnList(List.of("id", "attr1", "attr2", "attr3", "attr4"))
                    .bodyColumnList(List.of(
                            List.of("New York", 1L, 2L, 3L, 4L),
                            List.of("California", 2L, 3L, 4L, 5L),
                            List.of("Texas", 123L, 123L, 123L, 123L),
                            List.of("Arizona", 99L, 23L, 12L, 51L),
                            List.of("Indiana", 41L, 12L, 0L, 99L),
                            List.of("Washington", 511L, 232L, 32L, 41L),
                            List.of("Oklahoma", 11L, 22L, 33L, 45L)
                    ))
                    .build();

            ddlMapper.createDynamicTable(map);
            ddlMapper.batchInsertWithJoin(map);
        });
    }



}