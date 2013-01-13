package com.github.javarch.support;

import java.text.Normalizer;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Lucas <i>luksrn@gmail.com</i>
 * 
 *
 */
@Component
public class SlugGenerator {

	public String encode(String str) {
		Pattern p = Pattern.compile("\\p{InCombiningDiacriticalMarks}+",Pattern.UNICODE_CASE);
		Pattern p2 = Pattern.compile("\\p{Punct}+", Pattern.UNICODE_CASE);
		Pattern p3 = Pattern.compile("\\s+", Pattern.UNICODE_CASE);

		// Decompose any funny characters.
		String link = Normalizer.normalize(str, Normalizer.Form.NFD)
				.replaceAll(p.pattern(), "") // remove all the diacritic marks
				.replaceAll(p2.pattern(), " ").trim() // transform the punctuation into spaces first, so that we can trim some ending or beginning punctuation
				.replaceAll(p3.pattern(), "-") // and replace all the whitespace with a dash.
				.toLowerCase();

		return link;
	}

}
