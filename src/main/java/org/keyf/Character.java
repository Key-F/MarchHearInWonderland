package org.keyf;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class Character {

    private String name;
    //private Location location;
    private List<Item> itemNeeded;
    private List<Item> itemWanted;
    private String speech;

    Character(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject LocObj = new JSONObject(jsonTxt);
            this.name = (String) LocObj.get("Name");
            this.isGoingToParty = false;
            //this.location = Wonderland.getLocation((String) LocObj.get("Location"));
        }
        catch(Exception e) {};
    }

    public boolean isGoingToParty() {
        return isGoingToParty;
    }

    public void setGoingToParty(boolean goingToParty) {
        isGoingToParty = goingToParty;
    }

    private boolean isGoingToParty;


    public void say() {
        System.out.println(speech);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Item> getItemNeeded() {
        return itemNeeded;
    }

    public void setItemNeeded(List<Item> itemNeeded) {
        this.itemNeeded = itemNeeded;
    }

}
