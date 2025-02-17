public class DeskovaHra {
    private String nazev;
    private boolean koupena;
    private int oblibenost;

    public DeskovaHra(String nazev, boolean koupena, int oblibenost) {
        this.nazev = nazev;
        this.koupena = koupena;
        this.oblibenost = oblibenost;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public boolean isKoupena() {
        return koupena;
    }

    public void setKoupena(boolean koupena) {
        this.koupena = koupena;
    }

    public int getOblibenost() {
        return oblibenost;
    }

    public void setOblibenost(int oblibenost) {
        this.oblibenost = oblibenost;
    }
}
