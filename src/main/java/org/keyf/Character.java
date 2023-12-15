package org.keyf;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private Location location;
    private List<Item> itemNeeded;
    private List<Item> itemWanted;
    private String speech;
    private boolean isGoingToParty;

    Character(File file) {
        try {
            InputStream is = new FileInputStream(file);
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject LocObj = new JSONObject(jsonTxt);
            this.name = (String) LocObj.get("Name");
            this.isGoingToParty = false;
            //this.location = Wonderland.getLocation((String) LocObj.get("Location"));
        } catch (Exception e) {
        }
        ;
    }

    Character(String name) {
        final File folder = new File("characters/");
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.getPath()
                    .toLowerCase()
                    .contains(name.toLowerCase().replaceAll("\\s+", ""))) {
                try {
                    InputStream is = new FileInputStream(fileEntry);
                    String jsonTxt = IOUtils.toString(is, "UTF-8");
                    JSONObject LocObj = new JSONObject(jsonTxt);
                    this.name = (String) LocObj.get("Name");
                    this.speech = (String) LocObj.get("Speech");
                    this.isGoingToParty = false;
                    this.itemWanted = new ArrayList<>();
                    this.itemNeeded = new ArrayList<>();
                    for (Object obj : (JSONArray) LocObj.get("ItemsNeeded")) {
                        if (obj.equals("")) continue;
                        this.itemNeeded.add(new Item((String) obj));
                    }
                    for (Object obj : (JSONArray) LocObj.get("ItemsWanted")) {
                        if (obj.equals("")) continue;
                        Item i = new Item((String) obj);
                        this.itemWanted.add(i);
                        //Wonderland.itemForParty.add(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isGoingToParty() {
        return isGoingToParty;
    }

    public void setGoingToParty(boolean goingToParty) {
        if (goingToParty) System.out.println("Okay, I'll visit your party");
        isGoingToParty = goingToParty;
    }


    public void say() {
        System.out.println(speech);

        System.out.println("I want this for a party: ");
        for (Item i : itemWanted) {
            System.out.println(i.getNameFullName());
        }

        System.out.println("I need this before visiting a party: ");
        for (Item i : itemNeeded) {
            System.out.println(i.getNameFullName());
        }
    }

    public boolean getItem(Item i) {
        if (!itemNeeded.contains(i)) {
            System.out.println("I dont need " + i.getNameFullName());
            return false;
        }
        if (i.getNameFullName().contains("Broken")) {
            System.out.println(i.getNameFullName() + " is broken!");
            return false;
        }
        else{
            itemNeeded.remove(i);
            System.out.println(name + " took your " + i.getNameFullName() + " with a smile");
            return true;
        }
    }

    public List<Item> getItemWanted() {
        return itemWanted;
    }

    public void setItemWanted(List<Item> itemWanted) {
        this.itemWanted = itemWanted;
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
