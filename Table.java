package exampleproject;

public class Table{

    protected int seats;
    protected String quality;

    public Table(int seats, String quality){
        this.seats = seats;
        this.quality = quality;
    }

    public int getSeats() {
        return this.seats;
    }

    public String getQuality() {
        return this.quality;
    }

    @Override
    public String toString() {
        return "Table [quality=" + quality + ", seats=" + seats + "]";
    } 
}
