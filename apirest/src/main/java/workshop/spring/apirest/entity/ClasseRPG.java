package workshop.spring.apirest.entity;

public enum ClasseRPG {
    MAGO,
    GUERRERO,
    LADINO,
    BRUXO,
    CLERIGO,
    BARDO,
    ARQUEIRO;

    ClasseRPG() {
    }

    @Override
    public String toString() {
        return "ClasseRPG{} " + super.toString();
    }
}
