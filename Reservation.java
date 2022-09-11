package exampleproject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reservation {
    
    private int pers;
    private int persFit;
    private Person booker;
    private String timeSt;
    private String timeSp;
    private LocalDate date;
    private String qual;
    private List<String> reservations;
    
    private final List<String> qualityList = Arrays.asList("Normal", "Poor", "Premium");
    
    private final List<String> times =  
            Arrays.asList("10:00", "11:00", "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00");
    

    public Reservation(int pers, int persFit, Person booker, String timeSt, String timeSp, LocalDate date, String qual, List<String> reservations) {
        this.booker = booker; 
        this.persFit = persFit;
        this.reservations = reservations;

        if (checkTimeReal(timeSt, timeSp)){
            this.timeSt = timeSt;
            this.timeSp = timeSp;
        }
        else{
            throw new IllegalArgumentException("Invalid time"); 
        }
          
        if (checkDate(date)){
            this.date = date;
        }
        else{
            throw new IllegalArgumentException("Invalid date"); 
        }
        
        if (qualityList.contains(qual)){
            this.qual = qual;
        }
        else{
            throw new IllegalArgumentException("Invalid quality"); 
        }

        if ((pers > 0 && pers < 8)){
            try {
                this.pers = pers;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else{
            throw new IllegalArgumentException("Too few/many people"); 
        }


    }

    private boolean checkTimeReal(String timeSt, String timeSp){
        if (!(timeSt.equals(null) || timeSp.equals(null))){
            if ((times.indexOf(timeSt) < times.indexOf(timeSp))){
                return true;
            }
        }
        return false;
    }

    private boolean checkDate(LocalDate date){  
        if (date == null){
            return false;
        }      
        if (date.isBefore(LocalDate.now()) || (date.getDayOfWeek() == DayOfWeek.SUNDAY)){
            return false;
        } 
        return true; 
    }

    public String priceCalc(){
        double price = 0;
        if (this.qual == "Premium"){
            price = (100 + (this.getPers()*20) + (this.TimeDiff()*10) + this.persFit*10);
        }

        if (this.qual == "Normal"){
            price = (100 + (this.getPers()*10) + (this.TimeDiff()*5) + this.persFit*10);
        }

        return String.valueOf(price) + "kr";
    }

    public int getPers() {
        return this.pers;
    }

    public int getPersFit() {
        return this.persFit;
    }

    private int TimeDiff() {
        return (times.indexOf(this.timeSp) - times.indexOf(this.timeSt));
    }

    public String getQual() {
        return this.qual;
    }

    public Person getBooker(){
        return this.booker;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public String getTimeSt() {
        return this.timeSt;
    }

    public String getTimeSp() {
        return this.timeSp;
    }

    private List<String> getReservations() {
        return this.reservations;
    }

    public void setReservations(List<String> reservations) {
        this.reservations = reservations;
    }

    public boolean checkIfTableRes(){
        for (String string : this.getReservations()) {
            String[] FileInString = string.substring(7).split(", ");
            if (this.getDate().toString().equals(FileInString[0]) && Integer.toString(this.getPersFit()).equals(FileInString[2]) && this.getQual().equals(FileInString[3])){
                List<String> midtimesThis =  new ArrayList<String>();
                List<String> midtimesRes = new ArrayList<String>();
                for (int i = times.indexOf(FileInString[4]); i < (times.indexOf(FileInString[5])) + 1; i++) {
                    midtimesThis.add(times.get(i));
                }
                for (int i = times.indexOf(this.getTimeSt()); i < (times.indexOf(this.timeSp)) + 1; i++) {
                    midtimesRes.add(times.get(i));
                }
                List<String> newList = Stream.concat(midtimesRes.stream(), midtimesThis.stream())
                                        .distinct()
                                        .collect(Collectors.toList());

                if (newList.size() != (midtimesRes.size() + midtimesThis.size())){
                    return true;
                    }
                }
            }
        return false; 
        }
        
    @Override
    public String toString() {
        return "Date : " + date + "\nTime : " + timeSt + "-" + timeSp+ 
        "\nPeople : " + pers + "\nQuality : " + qual;
    }

    public String PrinttoString() {
        return "Table: " + date + ", " + pers + ", " + persFit + ", " + qual  + ", " + timeSt  + ", " + timeSp;
    }
}