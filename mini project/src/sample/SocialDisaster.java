package sample;

public class SocialDisaster extends LifeEvent {
    private double livelyhoodScore;

    public SocialDisaster(String description, boolean impact, String[] affectedBusinesses, double livelyhoodScore) {
        super(description, impact, affectedBusinesses);
        this.livelyhoodScore = livelyhoodScore;
    }

    public double getLivelyhoodScore() {
        return livelyhoodScore;
    }

    public void setLivelyhoodScore(double livelyhoodScore) {
        this.livelyhoodScore = livelyhoodScore;
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

        s = "\nSOCIAL DISASTER: \n" + getDescription() + "\nImpact: " + impactString + "\nLivelyhood Score: " + livelyhoodScore +  "\nBusinesses Affected: " + businessesAffectedString+"\n";
        return s;
    }

}
