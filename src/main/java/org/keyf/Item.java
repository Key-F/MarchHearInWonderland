package org.keyf;

import java.util.List;

public class Item {

    private String name;
    private boolean isFixed;
    private boolean isNeededForParty;
    private List<Character> whoNeeds;


    public void fixItem() {
        if (isFixed) {
            System.out.println("Your " + name + " is already in a good shape");
        }
        else {
            isFixed = true;
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
        return isFixed;
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }

    public List<Character> getWhoNeeds() {
        return whoNeeds;
    }

    public void setWhoNeeds(List<Character> whoNeeds) {
        this.whoNeeds = whoNeeds;
    }
}
