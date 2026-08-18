package project.courier;
import java.util.List;


public class OrderCreate {
    private String firstName = "Настя";
    private String lastName = "Иванова";
    private String address = "Москва, ул Куприна 24к1, к1";
    private int metroStation = 12;
    private String phone = "8 900 500 66 66";
    private String rentTime = "1";
    private String deliveryDate = "2026-08-18";
    private String comment = "Доставьте чистый и заряженный самокат";
    private List <String> color;

    public OrderCreate (List<String> color){

        this.color=color;
    }
    public OrderCreate() {}

    public int getMetroStation() {

        return metroStation;
    }
    public void setMetroStation(int metroStation) {
        this.metroStation = metroStation;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
}
public String getLastName(){

        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {

        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
}
public String getPhone (){
    return phone;}
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getRentTime(){
        return rentTime;}
    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }
    public String getDeliveryDate(){
        return deliveryDate;}
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public String getComment(){
        return comment;}
    public void setComment(String comment) {
        this.comment = comment;
}
public List<String> getColor() {
    return color;
}
public void setColor(List<String> color) {
    this.color = color;
}}