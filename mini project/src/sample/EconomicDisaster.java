package sample;

public class EconomicDisaster extends LifeEvent {
    private double GDPChange;

    public EconomicDisaster(String description, boolean impact, String[] affectedBusinesses, double GDPChange) {
        super(description, impact, affectedBusinesses);
        this.GDPChange = GDPChange;
    }

    public double getGDPChange() {
        return GDPChange;
    }

    public void setGDPChange(double GDPChange) {
        this.GDPChange = GDPChange;
    }

    @Override
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

        s = "\nECONOMIC DISASTER: \n" + getDescription() + "\nImpact: " + impactString + "\nGDP Change: " + GDPChange +  "\nBusinesses Affected: " + businessesAffectedString +"\n";
        return s;
    }
}
