import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeskovaHraFrame extends JFrame{
    private JPanel panelMain;
    private JTextField tfNazev;
    private JLabel lbNazev;
    private JLabel lbKoupeno;
    private JCheckBox chbKoupeno;
    private JLabel lbOblibenost;
    private JRadioButton rb1;
    private JRadioButton rb2;
    private JRadioButton rb3;
    private JButton btDalsi;
    private JButton btZpet;
    private JButton btUlozit;
    private JButton btNovyZaznam;
    private ButtonGroup oblibenostGroup;
    private int pocet = 0;

    private List<DeskovaHra> seznamDeskovek = new ArrayList<>();

    public DeskovaHraFrame() {
        initComponents();
    }

    private void initComponents() {
        setContentPane(panelMain);
        setTitle("Seznam deskových her");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);

        oblibenostGroup = new ButtonGroup();
        oblibenostGroup.add(rb1);
        oblibenostGroup.add(rb2);
        oblibenostGroup.add(rb3);


        btUlozit.addActionListener(e -> ulozDeskovku(pocet));
        btDalsi.addActionListener(e -> dalsiZaznam());
        btZpet.addActionListener(e -> zpetZaznam());
        btNovyZaznam.addActionListener(e -> pridatZaznam());
    }

    private void dalsiZaznam() {
        if(pocet == seznamDeskovek.size() - 1) {
            JOptionPane.showMessageDialog(this, "Nejsou k dispozici další záznamy!");
        } else {
            try {
                pocet++;
                nactiDoOkna();
            } catch (DeskovaHraException e) {
                vyhlasChybu(e);
            }
        }
    }

    private void zpetZaznam() {
        if(pocet == 0) {
            JOptionPane.showMessageDialog(this, "Nejsou k dispozici další záznamy!");
        } else {
            try {
                pocet--;
                nactiDoOkna();
            } catch (DeskovaHraException e) {
                vyhlasChybu(e);
            }
        }
    }

    private void ulozDeskovku(int pocet) {
        seznamDeskovek.get(pocet).setNazev(tfNazev.getText());
        seznamDeskovek.get(pocet).setKoupena(chbKoupeno.isSelected());
        if(rb1.isSelected()) seznamDeskovek.get(pocet).setOblibenost(1);
        if(rb2.isSelected()) seznamDeskovek.get(pocet).setOblibenost(2);
        if(rb3.isSelected()) seznamDeskovek.get(pocet).setOblibenost(3);
    }

    public void pridejDeskovkuDoSeznamu(DeskovaHra deskovaHra) {
        seznamDeskovek.add(deskovaHra);
    }

    public void vyhlasChybu(DeskovaHraException e) {
        JOptionPane.showMessageDialog(this, e);
    }

    public void nactiDeskovky(String nazevSouboru, String oddelovac) throws DeskovaHraException {
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader("resources/" + nazevSouboru)))) {
            while (scanner.hasNextLine()) {
                String radek = scanner.nextLine();
                pridejDeskovkuDoSeznamu(parseDeskovka(radek, oddelovac));
            }
            if (seznamDeskovek != null) {
                nactiDoOkna();
            } else throw new DeskovaHraException("Seznam deskovek je prázdný!");
        } catch (FileNotFoundException e) {
            throw new DeskovaHraException("Nenalezený soubor");
        }
    }

    private DeskovaHra parseDeskovka(String radek, String oddelovac) throws DeskovaHraException {
        String[] polozky = radek.split(oddelovac);
        if(polozky.length != 3) {
            throw new DeskovaHraException("Špatný počet položek v souboru!");
        }
        String nazev = polozky[0].trim();
        boolean koupena = Boolean.parseBoolean(polozky[1].trim());
        int oblibenost = Integer.parseInt(polozky[2].trim());
        if(oblibenost > 3 || oblibenost < 1) throw new DeskovaHraException("Rozsah oblíbenosti: 1-3!!");

        return new DeskovaHra(nazev, koupena, oblibenost);
    }

    private void nactiDoOkna() throws DeskovaHraException {
        tfNazev.setText(seznamDeskovek.get(pocet).getNazev());
        if(seznamDeskovek.get(pocet).isKoupena()) chbKoupeno.setSelected(true);
        switch (seznamDeskovek.get(pocet).getOblibenost()) {
            case 1:
                rb1.setSelected(true);
                break;
            case 2:
                rb2.setSelected(true);
                break;
            case 3:
                rb3.setSelected(true);
                break;
            default:
                throw new DeskovaHraException("Nastala chyba při načítání oblíbenosti");
        }
    }

    private void pridatZaznam() {
        seznamDeskovek.add(new DeskovaHra("Doplňte informace", false, 1));
        pocet = seznamDeskovek.size() - 1;
        try {
            nactiDoOkna();
        } catch (DeskovaHraException e) {
            System.err.println("ajajaj");
        }
    }

    //ve formu shiftem označíme, co má být v button group => vlevo ve sloupečku Button Group => New.. => zadáme název => ok
}
