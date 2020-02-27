package translatorPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import translatorPackage.PhrasesBean;

@Configuration

@RestController
public class ControllerI18N {
	@Autowired
	private MessageSource messageSource;
	
    public ControllerI18N(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
	
    // I18N - gets phrase / word to translate and returns translation
	@GetMapping(path = "/translator-I18N/translate")
	public String translateWords(@RequestParam String phrase, @RequestHeader(name= "Accept-Language", required = false) Locale locale) {
		try {
			return messageSource.getMessage(phrase, new Object[]{phrase}, locale);
		}
		catch(Exception e) {
			return "Can't find this phrase / word";
		}
	}
	
	// GET method 1 - gets phrase + translation and adds them to API with help of HashMap
	@RequestMapping(value = "/translator-I18N/addPhrase", method = RequestMethod.GET)
	public HashMap<String, String> putPhrase(String phrase, String translation) throws IOException {
		PhrasesBean.translatePhrases.put(phrase, translation);
		System.out.println(PhrasesBean.translatePhrases);
		return PhrasesBean.translatePhrases;
	}
	
	// GET method 2 - gets phrase and adds it to API with help of ArrayList
	@RequestMapping(value = "/translator-I18N/addPhrase2", method = RequestMethod.GET)
	public ArrayList<String> addPhrase (String phrase) throws IOException {
		PhrasesBean.phrasesToTranslate.add(phrase);
		System.out.println(PhrasesBean.phrasesToTranslate);
		return PhrasesBean.phrasesToTranslate;
	}
	
	// GET method 3 - gets phrase and adds it to API with help of PathVariable
	@GetMapping(path = "/translator-I18N/addPhrase3/{phrase}")
	public String translatePathVariable(@PathVariable String phrase) {
		System.out.println(phrase);
		return (phrase); 
	}
	
}