/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.bll.utility.languages;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fauxtistic
 */
public class Localizer {    
    
    /* To use, write like this in controller for every fxid (example):
    
    private void translateText(Language lang) {
    newButton.setText(localizer.translate(newButton.getId(), lang));
    }
    
    And in .txt file, in the format (fxid=language1;;language2) :
    
    newButton=Click here;;Tryk her
    
    and so forth with every language in same horizontal position in file, separated by ;;
    and every new fxid in next row
    Note: case sensitive!
    
    */
    
    private Properties props;
    private FileReader reader;

    //Add languages as needed, with sequential indexes
    public enum Language {
        ENGLISH(0), DANSK(1);

        private int index;

        Language(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public Localizer(String filePath) {
        try {
            props = new Properties();
            reader = new FileReader(filePath);
            props.load(reader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Localizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Localizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String translate(String node, Language lang) {
        String text = "?";
        String nodeId = props.getProperty(node);
        if (nodeId != null) {
            String[] versions = nodeId.split(";;");
            if (versions.length > lang.getIndex()) {
                if (!versions[lang.getIndex()].trim().isEmpty()) {
                    text = versions[lang.getIndex()];
                }
            }
        }
        return text;
    }
}
