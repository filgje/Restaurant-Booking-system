package exampleproject;

public class PTable extends Table{

    public PTable(int seats) {
        super(seats, "Poor");
    }

    @Override
    public String toString() {
        return "Poor table \nwith " + seats + " seats.";
    }

    
}
