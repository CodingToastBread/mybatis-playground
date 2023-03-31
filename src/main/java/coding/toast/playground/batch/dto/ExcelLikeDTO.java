package coding.toast.playground.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.Arrays;
import java.util.List;

@Alias("ExcelLikeDTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelLikeDTO {

    private String schemaName;

    private String tableName;

    private List<String> headerColumnList;

    private List<List<Object>> bodyColumnList;

    public String getHeaderColumnConcatString() {
        String insertHeaderColString = this.headerColumnList
                .stream().map(s -> "\"" + s + "\"").reduce((s, s2) -> s + ", " + s2)
                .orElseThrow(() -> new RuntimeException("your header is weird..."));

        return getQuestionMark(headerColumnList.size());

        // return """
        //         insert into "%s"."%s" (%s)
        //         values (%s)
        //         """.formatted(
        //         this.schemaName,
        //         this.tableName,
        //         insertHeaderColString,
        //         preparedQuestionMark
        // );
    }

    private String getQuestionMark(int headerWidth) {
        String[] strings = new String[headerWidth];
        Arrays.fill(strings, "?");
        return String.join(",", strings);
    }


}
