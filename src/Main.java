public class Main {
    public static void main(String[] args) {
        DeskovaHraFrame frame = new DeskovaHraFrame();
        try {
            frame.setVisible(true);
            frame.nactiDeskovky("deskovky.txt", ";");
        } catch (DeskovaHraException e) {
            frame.vyhlasChybu(e);
        }
    }
}