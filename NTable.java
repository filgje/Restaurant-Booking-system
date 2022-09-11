package exampleproject;

public class NTable extends Table {

    public NTable(int seats) {
        super(seats, "Normal");
    }

    @Override
    public String toString() {
        return "Normal table \nwith " + seats + " seats.";
    }    
}
