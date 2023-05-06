package coding.toast.playground.batch;

import coding.toast.playground.batch.dto.ExcelLikeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Mapper
public interface DDLBatchMapper {

    // int createTable(@Param("map") Map<String, Object> map);

    int createTable(ExcelLikeDTO map);

    int createTableBatch(ExcelLikeDTO dto);

    int createDynamicTable(ExcelLikeDTO dto);

    int batchInsert(ExcelLikeDTO dto);

    int batchInsertWithJoin(ExcelLikeDTO dto);
    
}
