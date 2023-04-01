package coding.toast.playground.batch;

import coding.toast.playground.batch.dto.ExcelLikeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.xml.transform.Templates;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DDLBatchMapper.xml
 */
@SpringBootTest
public class DDLBatchMapperTest {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @Autowired
    private DDLBatchMapper ddlMapper;



    @Test
    @Transactional // by the way, postgresql supports ddl rollback!!! amazing!
    @Rollback(false)
    @DisplayName("create table and insert with... jdbcTemplate")
    public void createTableWithJdbcTest() {
        
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
        
        List<String> tableCreateStrList = new ArrayList<String>();
        
        // for table name
        tableCreateStrList.add(map.getSchemaName());
        tableCreateStrList.add(map.getTableName());
        
        // for primary key constraint name
        tableCreateStrList.add(map.getSchemaName());
        tableCreateStrList.add(map.getTableName());
        
        // for table header column list
        tableCreateStrList.addAll(map.getHeaderColumnList());
        
        jdbcTemplate.execute("""
        DO $$BEGIN
            drop table if exists "%s"."%s";
            create table "%s"."%s" (
                "%s" character varying(20),
                "%s" int ,
                "%s" int ,
                "%s" int ,
                "%s" int ,
                geom geometry(Point, 5186)
            );
            alter table "%s"."%s" add constraint "pk_for_%s_%s" primary key (%s);
            CREATE INDEX "geometry_idx_on_%s_%s" ON  "%s"."%s" USING gist (geom);
        END$$
        """.formatted(
            map.getSchemaName(),
            map.getTableName(),
            map.getSchemaName(),
            map.getTableName(),
            map.getHeaderColumnList().get(0),
            map.getHeaderColumnList().get(1),
            map.getHeaderColumnList().get(2),
            map.getHeaderColumnList().get(3),
            map.getHeaderColumnList().get(4),
            map.getSchemaName(),
            map.getTableName(),
            map.getSchemaName(),
            map.getTableName(),
            map.getHeaderColumnList().get(0),
            map.getSchemaName(),
            map.getTableName(),
            map.getSchemaName(),
            map.getTableName()
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
        // jdbcTemplate.batchUpdate(jdbcBatchQuerySqlString, list); // want to test rollback? uncomment this code!
        
    }

    private static String getQuestionMark(int headerWidth) {
        String[] strings = new String[headerWidth];
        Arrays.fill(strings, "?");
        return String.join(",", strings);
    }


    @Test
    @Transactional
    @Commit
    @DisplayName("create table and insert with... MyBatis")
    public void createTableTest2() {
        
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
    }
    
    @Test
    @Transactional
    @Rollback(false)
    @DisplayName("postgresql ddl transaction roll back test")
    public void createTableTest3() {
        
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
        
        System.out.println("is AOP Proxy????? ===> " + AopUtils.isAopProxy(this));
        
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
            ddlMapper.batchInsertWithJoin(map);// because of created table has a primary key constraint on id column
                                                // it will throw exception (DuplicateKey? maybe...), and then rollback every thing.
    }
}