/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applepushsender;
import java.util.logging.Level;
import java.util.logging.Logger;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;

/**
 *
 * @author Sinri
 */
public class ApplePushSender {
    
    static SenderFrame app;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        app=new SenderFrame();
        app.setVisible(true);
        
        /*
        try {
            // TODO code application logic here
            boolean isProduction=false;
            Push.alert("Hello World!", "WhereWeArePush.p12", "20070715", isProduction, "fa93cd91f6a2ee45bd7f4b25f06a7098c1029cc2c327e3424576c60719d14e01");
        } catch (CommunicationException ex) {
            Logger.getLogger(ApplePushSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeystoreException ex) {
            Logger.getLogger(ApplePushSender.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
}
