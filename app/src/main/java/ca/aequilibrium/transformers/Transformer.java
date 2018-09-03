package ca.aequilibrium.transformers;

public class Transformer {
    public enum Team {AUTOBOTS, DECEPTICONS}

    private String name;
    private Team team;
    private int strength;
    private int intelligence;
    private int speed;
    private int endurance;
    private int rank;
    private int courage;
    private int firepower;
    private int skill;

    private boolean alreadyFight;
    private boolean eliminated;

    public Transformer() {
    }

    public Transformer(String name, Team team, int strength, int intelligence, int speed,
                       int endurance, int rank, int courage, int firepower, int skill) {
        this.name = name;
        this.team = team;
        this.strength = strength;
        this.intelligence = intelligence;
        this.speed = speed;
        this.endurance = endurance;
        this.rank = rank;
        this.courage = courage;
        this.firepower = firepower;
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCourage() {
        return courage;
    }

    public void setCourage(int courage) {
        this.courage = courage;
    }

    public int getFirePower() {
        return firepower;
    }

    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setAlreadyFight(boolean alreadyFight) {
        this.alreadyFight = alreadyFight;
    }

    public boolean isAlreadyFight() {
        return alreadyFight;
    }

    public int getOverallRating() {
        return strength + intelligence + speed + endurance + firepower;
    }
}
