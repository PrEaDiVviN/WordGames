package model;

public class Synonym {
    private int idSinonim;
    private int idCuvant;
    private String tipSinonime;
    private String textSinonime;

    public Synonym(int idSinonim, int idCuvant, String tipSinonime, String textSinonime) {
        this.idSinonim = idSinonim;
        this.idCuvant = idCuvant;
        this.tipSinonime = tipSinonime;
        this.textSinonime = textSinonime;
    }

    public Synonym(int idCuvant, String tipSinonime, String textSinonime) {
        this.idCuvant = idCuvant;
        this.tipSinonime = tipSinonime;
        this.textSinonime = textSinonime;
    }

    public int getIdSinonim() {
        return idSinonim;
    }

    public void setIdSinonim(int idSinonim) {
        this.idSinonim = idSinonim;
    }

    public int getIdCuvant() {
        return idCuvant;
    }

    public void setIdCuvant(int idCuvant) {
        this.idCuvant = idCuvant;
    }

    public String getTipSinonime() {
        return tipSinonime;
    }

    public void setTipSinonime(String tipSinonime) {
        this.tipSinonime = tipSinonime;
    }

    public String getTextSinonime() {
        return textSinonime;
    }

    public void setTextSinonime(String textSinonime) {
        this.textSinonime = textSinonime;
    }

    @Override
    public String toString() {
        return "Sinonim: " + this.getTextSinonime() + "\nTipul: " + this.getTipSinonime() + "\nId sinonim: " + this.getIdSinonim() + "\nId word: " + this.getIdCuvant();
    }
}
