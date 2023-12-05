
public class Main {
    public static void main(String[] args) {
        StaffReader staffReader = new StaffReader("StaffFileTest.txt");
        EcsSim ecsSim = new EcsSim(2000, staffReader.readStaffMarket());
        ecsSim.simulate(200);
        /*EcsSim ecsSim = new EcsSim(10000);
        ecsSim.simulate(50);*/
    }
}