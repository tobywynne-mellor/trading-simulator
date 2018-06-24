package sample;

import java.io.Serializable;

public class LifeEvent implements Serializable{
    private String description;
    private boolean impact;
    private String[] affectedBusinesses;

    public LifeEvent(String description, boolean impact, String[] affectedBusinesses) {
        this.description = description;
        this.impact = impact;
        this.affectedBusinesses = affectedBusinesses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getImpact() {
        return impact;
    }

    public void setImpact(boolean impact) {
        this.impact = impact;
    }

    public String[] getAffectedBusinesses() {
        return affectedBusinesses;
    }

    public void setAffectedBusinesses(String[] affectedBusinesses) {
        this.affectedBusinesses = affectedBusinesses;
    }

    public String summarise()
    {
        String s = "";
        String businessesAffectedString = "";
        String impactString = "negative";

        for (String b: getAffectedBusinesses())
        {
            businessesAffectedString = businessesAffectedString +" "+b;
        }

        if(getImpact()){
            impactString = "positive";
        }

        s = "\nLIFE EVENT: \n" + getDescription() + "\nImpact: " + impactString + "\nBusinesses Affected: " + businessesAffectedString+"\n";
        return s;
    }


}
