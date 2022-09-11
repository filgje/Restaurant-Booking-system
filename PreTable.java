public class PreTable extends Table{

    public PreTable(int seats) {
        super(seats, "Premium");
    }

    @Override
    public String toString() {
        return "Premium table \nwith " + seats + " seats.";
    }

}
