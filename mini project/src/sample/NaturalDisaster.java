package sample;

public class NaturalDisaster extends LifeEvent {

    private int numberOfDeaths;
    private int numberInjured;
    private double cost;

    public NaturalDisaster(String description, boolean impact, String[] affectedBusinesses, int numberOfDeaths, int numberInjured, double cost) {
        super(description, impact, affectedBusinesses);
        this.numberOfDeaths = numberOfDeaths;
        this.numberInjured = numberInjured;
        this.cost = cost;
    }

    public int getNumberOfDeaths() {
        return numberOfDeaths;
    }

    public void setNumberOfDeaths(int numberOfDeaths) {
        this.numberOfDeaths = numberOfDeaths;
    }

    public int getNumberInjured() {
        return numberInjured;
    }

    public void setNumberInjured(int numberInjured) {
        this.numberInjured = numberInjured;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String summarise()
    {
        String s = "";
        String businessesAffectedString = "";
        String impactString = "negative";

        for (String b: getAffectedBusinesses())
        {
            businessesAffectedString = businessesAffectedString+" "+b;
        }

        if(getImpact()){
            impactString = "positive";
        }

        s = "\nNATURAL DISASTER: \n" + getDescription() + "\nImpact: " + impactString + "\nDeaths: " + numberOfDeaths + "\nInjured: " + numberInjured + "\nEstimated Cost of Damage: " + cost +  "\nBusinesses Affected: " + businessesAffectedString+"\n";
        return s;
    }
}
