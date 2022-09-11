package exampleproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Restaurant {
    private static Person user = null;
    private Collection<Table> TabInRes = new ArrayList<Table>();
        

    public Restaurant(){
        for (int i = 0; i < 3; i++) {
            Table midPoor = new PTable((((i+1)*3)-i));
            Table midNormal = new NTable((((i+1)*3)-i));
            Table midPrem = new PreTable((((i+1)*3)-i));
            this.TabInRes.add(midPoor);
            this.TabInRes.add(midNormal);
            this.TabInRes.add(midPrem);
        }
    }

    //Check if user is added to list
    public void addUser(Person person){
        user = person;
    }

    public Person getUser(){
        return user;
    }

    //Check number of available tables
    public List<Table> getAvailableTables(int pers, String qual){
        List<Table> availableTables = TabInRes.stream()
                                        .filter(t -> t.getQuality() == qual)
                                        .filter(tb -> tb.getSeats() >= pers)
                                        .toList();
        return availableTables;
    }
}

