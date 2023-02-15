package taal;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Taal is verantwoordelijk voor het vertalen van de applicatie.
 */
public class Taal {
	static Taal obj = new Taal();
    private static Locale locale;
    private static ResourceBundle resourceBundle;
    private static String name;

    /**
     * Constructor Taal.
     * @param 	taal
     * 			De gewenste taal.
     */
    public Taal(String taal) {
        setName(taal);
        setLocale(taal);
        setResourceBundle();
        
    }
    
    /**
     * Constructor Taal voor static object.
     */
    public Taal() {
    	
    }

    public Locale getLocale() {
        return locale;
    }

    private static void setLocale(String locale) {
        if(locale.equals("en")){
            Taal.locale = new Locale("en","US");
        }else{
            Taal.locale = new Locale("nl","BE");
        }
    }

    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
    
    public void setRB(String lang) {
    	if(lang.equals("en")) {
    		resourceBundle = ResourceBundle.getBundle("taal/Bundle_en_US",Taal.locale);
    	}else {
    		resourceBundle = ResourceBundle.getBundle("taal/Bundle_nl_BE",Taal.locale);
    	}
    }

    private void setResourceBundle() {
        if(Taal.name.equals("en")){
            resourceBundle = ResourceBundle.getBundle("taal/Bundle_en_US",Taal.locale);
        }else {
            resourceBundle = ResourceBundle.getBundle("taal/Bundle_nl_BE",Taal.locale);

        }
    }

    private String getName() {
        return Taal.name;
    }

    public static void setName(String taal) {
        if(taal!= "en" && taal!= "nl"){
            throw new IllegalArgumentException("U moet kiezen tussen Engels of Nederlands");
        }else Taal.name = taal;
    }
    
    /**
     * Tekst vertalen a.d.h.v. de key die wordt opgezocht in de ResourceBundles.
     * @param 	key
     * 			Key waarde die gelinkt is aan tekst in de ResourceBundles.
     * @return	Tekst die gelinkt is aan de key wordt geretourneerd.
     */
    public static String vertaal(String key){
    return resourceBundle.getString(key);
    }

	public static Taal getInstance() {
		return obj;
	}
}