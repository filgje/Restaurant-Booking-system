
public class Person implements Comparable<Person>{
    private String username;
    private String password;
    private boolean isAdmin = false;
    private int personRes = 0;

    public Person(String username, String password){
        if (username != "" && password != ""){
            if (password.length() > 3 && password.length() < 21){
                this.username = username;
                this.password = password;
            }
            else{
                throw new IllegalArgumentException("Password needs to be between 4 and 20 letters");
            }
            
        }
        else{
            throw new IllegalStateException("Username or Password is empty");
        }

        if (username.equals("Admin") && password.equals("AdminPass")){
            isAdmin = true;
        }
        
    }

    public boolean isAdmin(){
        if (isAdmin){
            return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setUsername(String user){
        if (!this.username.equals(user)){
            this.username = user;
        }
        else{
            throw new IllegalArgumentException("You are already the user");
        }
    }

    public void setPassword(String pass){
        if (pass.length() > 3 && pass.length() < 21){
            this.password = pass;
        }
        else{
            throw new IllegalArgumentException("Password needs to be between 4 and 20 letters");
        }
    }

    public boolean sameUser(Person user){
        if (compareTo(user) == 1){
            return true;
        }
        return false;
    }
    
    @Override
    public int compareTo(Person user) {
        if (user.getUsername().equals(this.getUsername()) && user.getPassword().equals(this.getPassword())){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Person [password=" + password + ", username=" + username + "]";
    }

    public boolean cantMakeRes() {
        if (personRes > 2 && !isAdmin){
            return true;
        }
        return false;
    }

    public void setPersonRes(String loadRes) {
        String[] mid = loadRes.split(";");
        personRes = mid.length;
    }
}
