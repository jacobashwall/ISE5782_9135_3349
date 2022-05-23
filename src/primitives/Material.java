package primitives;

/**
 * Class that represents the material of a geometric object.
 * The class is PDS.
 */
public class Material {
    //public fields
    /**
     * diffusive coefficient
     */
    public Double3 kD = Double3.ZERO;
    /**
     * specular coefficient
     */
    public Double3 kS = Double3.ZERO;
    /**
     * transparency coefficient
     */
    public Double3 kT = Double3.ZERO;
    /**
     * reflection coefficient
     */
    public Double3 kR = Double3.ZERO;
    /**
     * diffusive glass coefficient
     */
    public double kDg = 0;
    /**
     * Glossy surface coefficient
     */
    public double kSg = 0;
    /**
     * shininess coefficient
     */
    public int nShininess = 0;

    /**
     * Setter for the kD field
     * @param kD parameter for the kD field
     * @return The object itself
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for the kD field
     *
     * @param kD double parameter for all three values in the kD field
     * @return The object itself
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for the kS field
     * @param kS parameter for the kS field
     * @return The object itself
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for the kS field
     * @param kS double parameter for all three values in the the kS field
     * @return The object itself
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for the kT field
     * @param kT parameter for the kT field
     * @return The object itself
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Setter for the kT field
     * @param kT double parameter for all three values in the kT field
     * @return The object itself
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for the kR field
     * @param kR parameter for the kR field
     * @return The object itself
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Setter for the kR field
     * @param kR double parameter for all three values in the kR field
     * @return The object itself
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }


    /**
     * Setter for the kDg field
     *
     * @param kDg double parameter value
     * @return The object itself
     */
    public Material setKdG(double kDg) {
        this.kDg = kDg;
        return this;
    }

    /**
     * Setter for the kSg field
     * @param kSg double parameter value
     * @return The object itself
     */
    public Material setKsG(double kSg) {
        this.kSg = kSg;
        return this;
    }


    /**
     * Setter for the nShininess field
     * @param nShininess parameter for the nShininess field
     * @return The object itself
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }


}
