/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.bll.utility.languages;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author math2
 */



public class LangDanish implements ILanguage{

    public enum Language{
        EN, DK
    }
    
    private static final String DICTIONARY = "/attendance_recorder/resources/translation_EN_DK.properties";
    private final Properties translator = new Properties();

    public LangDanish(Language from, Language to) {
    
        String translationFile = String.format(DICTIONARY, from, to);
        try (InputStream is = getClass().getResourceAsStream(translationFile)) {
            translator.load(is);
        } catch (final IOException e) {
            throw new RuntimeException("Could not read: " + translationFile, e);
        }
               
    }
    
    

    

    
    public String[] Translate(String toBeTranlated) {
    String[] source = normalizeText(toBeTranlated);
    List<String> translation = new ArrayList<>();
        for (String word : source) {
            translation.add(translateWord(word));
        }
        return translation.toArray(new String[source.length]);
    }
    
    private String translateWord(String sourceWord) {
        String value = (String) translator.get(sourceWord);
        String translatedWord;
        if (value != null) {
            translatedWord = String.valueOf(value);
        }
        else {
            // if no translation is found, add the source word with a question mark
            translatedWord = sourceWord + "?";
        }
        
        return translatedWord;
    }
    
    private String[] normalizeText(String text){
        String alphaText = text.replace("[^A-Za-z]", " ");
        return alphaText.split("\\s+");
    }
    
}
