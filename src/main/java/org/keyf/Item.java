package org.keyf;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class Item {

    private String name;
    private boolean isBroken;
    private boolean isNeededForParty;
    private List<Character> whoNeeds;

    public Item(String name) {
        final File folder = new File("items/");

        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.getPath()
                    .toLowerCase()
                    .contains(name.toLowerCase().replaceAll("\\s+","")))
            try {
                InputStream is = new FileInputStream(fileEntry);
                String jsonTxt = IOUtils.toString(is, "UTF-8");
                JSONObject LocObj = new JSONObject(jsonTxt);
                this.name = (String) LocObj.get("Name");
                this.isBroken = (boolean) LocObj.get("isBroken");
                //this.location = Wonderland.getLocation((String) LocObj.get("Location"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Item(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject LocObj = new JSONObject(jsonTxt);
            this.name = (String) LocObj.get("Name");
            this.isBroken = (boolean) LocObj.get("isBroken");
            //this.location = Wonderland.getLocation((String) LocObj.get("Location"));
        }
        catch(Exception e) {};
    }

    public void fixItem() {
        if (!isBroken) {
            System.out.println("Your " + name + " is already in a good shape");
        }
        else {
            isBroken = true;
            System.out.println(name + " was fixed");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFixed() {
        return isBroken;
    }

    public void setFixed(boolean fixed) {
        isBroken = fixed;
    }

    public List<Character> getWhoNeeds() {
        return whoNeeds;
    }

    public void setWhoNeeds(List<Character> whoNeeds) {
        this.whoNeeds = whoNeeds;
    }
}
