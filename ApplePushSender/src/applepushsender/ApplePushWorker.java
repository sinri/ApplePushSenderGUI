/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applepushsender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushedNotifications;

/**
 *
 * @author Sinri
 */
public class ApplePushWorker {

    private Object keystore;
    private String keystore_pw;
    private boolean isProduction;

    public ApplePushWorker(Object KS, String KSPW, boolean isP) {
        this.keystore = KS;
        this.keystore_pw = KSPW;
        this.isProduction = isP;
    }

    public ApplePushWorker() {
        keystore = null;
        keystore_pw = null;
        isProduction = true;
        this.getConfig();
    }

    public final void getConfig() {
        BufferedReader br = null;
        try {
            File file = new File("push_config.txt");
            if (file.exists()) {
                br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    String[] kv = line.split("=");
                    if (kv != null && kv.length >= 2) {
                        String key = kv[0].trim();
                        String value = kv[1].trim();
                        if (key.equalsIgnoreCase("keystore")) {
                            this.keystore = value;
                        } else if (key.equalsIgnoreCase("password")) {
                            this.keystore_pw = value;
                        } else if (key.equalsIgnoreCase("is_production")) {
                            this.isProduction = value.equalsIgnoreCase("true");
                        }
                    }
                    line = br.readLine();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveConfig() {
        File file = new File("push_config.txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            if (this.getKeystore() != null) {
                bw.append("keystore=" + this.getKeystore().toString());
                bw.newLine();
            }
            if (this.getKeystore_pw() != null) {
                bw.append("password=" + this.getKeystore_pw());
                bw.newLine();
            }
            bw.append("is_production=" + (this.isIsProduction() ? "true" : "false"));
            bw.newLine();
        } catch (Exception ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isReady() {
        if (this.getKeystore() != null && this.getKeystore_pw() != null) {
            return true;
        } else {
            return false;
        }
    }

    public PushedNotifications pushAlert(String alert, Object devices) {
        try {
            return Push.alert(alert, getKeystore(), getKeystore_pw(), isIsProduction(), devices);
        } catch (CommunicationException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeystoreException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public PushedNotifications pushCommonPayload(String message, int badge, String sound, Object devices) {
        try {
            return Push.combined(message, badge, sound, getKeystore(), getKeystore_pw(), isIsProduction(), devices);
        } catch (CommunicationException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeystoreException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public PushedNotifications pushContentAvailable(Object devices) {
        try {
            return Push.contentAvailable(getKeystore(), getKeystore_pw(), isIsProduction(), devices);
        } catch (CommunicationException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeystoreException ex) {
            Logger.getLogger(ApplePushWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the keystore
     */
    public Object getKeystore() {
        return keystore;
    }

    /**
     * @param aKeystore the keystore to set
     */
    public void setKeystore(Object aKeystore) {
        keystore = aKeystore;
        this.saveConfig();
    }

    /**
     * @return the keystore_pw
     */
    public String getKeystore_pw() {
        return keystore_pw;
    }

    /**
     * @param aKeystore_pw the keystore_pw to set
     */
    public void setKeystore_pw(String aKeystore_pw) {
        keystore_pw = aKeystore_pw;
        this.saveConfig();
    }

    /**
     * @return the isProduction
     */
    public boolean isIsProduction() {
        return isProduction;
    }

    /**
     * @param aIsProduction the isProduction to set
     */
    public void setIsProduction(boolean aIsProduction) {
        isProduction = aIsProduction;
        this.saveConfig();
    }
}
