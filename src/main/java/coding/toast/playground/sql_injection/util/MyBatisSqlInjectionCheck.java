package coding.toast.playground.sql_injection.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MyBatisSqlInjectionCheck {
    public static String check(String queryString) {
        if (StringUtils.hasText(queryString)) {
            return Pattern
                    .compile("(?i)(execute|select|from|where|insert|update|delete|create|truncate|drop|sleep|--|-|'|\"|\\*|;)")
                    .matcher(queryString)
                    .replaceAll("")
                    .trim();
        } else {
            return queryString;
        }
    }

    public static List<String> checkList(List<String> checkList) {

        if (!CollectionUtils.isEmpty(checkList)) { // 내부적으로 return (collection == null || collection.isEmpty()); 연산을 함으로 NPE 에 대해 안전하다.
            return checkList.stream()
                    .map(MyBatisSqlInjectionCheck::check)
                    .collect(Collectors.toList());
        } else {
            return checkList;
        }
    }

}
