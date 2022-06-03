package de.thm.mni.pis.vw6.utils;

public record Color(float r, float g, float b, float alpha) {
    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", alpha=" + alpha +
                '}';
    }
}
