package petgame;

public abstract class Location {
    protected String name;

    public Location(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void enter(GameManager game);
}