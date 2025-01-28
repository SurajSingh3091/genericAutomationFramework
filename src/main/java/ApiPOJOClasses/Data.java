package ApiPOJOClasses;

public class Data {
    private int year;
    private double price;
    private String CPUmodel;
    private String Harddisksize;
    public String getHardDiskSize() {
        return Harddisksize;
    }

    public void setHardDiskSize(String hardDiskSize) {
        this.Harddisksize = hardDiskSize;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCPUmodel() {
        return CPUmodel;
    }

    public void setCPUmodel(String CPUmodel) {
        this.CPUmodel = CPUmodel;
    }


}
