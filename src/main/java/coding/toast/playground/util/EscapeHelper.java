package coding.toast.playground.util;

import org.springframework.util.StringUtils;

public class EscapeHelper {
	
	public static String escape(String s) {
		return StringUtils.hasText(s) ? s.replaceAll("([%_])", "\\\\$1") : s;
	}
	
}
