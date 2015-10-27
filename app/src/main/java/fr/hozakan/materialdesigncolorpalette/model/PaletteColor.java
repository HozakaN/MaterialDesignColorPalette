package fr.hozakan.materialdesigncolorpalette.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PaletteColor implements Parcelable {

    private final int hex;
    private final String hexString;
    private final String baseName;
    private final String colorSectionName;
    private boolean isPrimaryColor;
    private boolean isPrimaryDarkColor;
    private boolean isAccentColor;

    public PaletteColor(String colorSectionName, final int hex, final String baseName,
                        boolean isPrimaryColor, boolean isPrimaryDarkColor, boolean isAccentColor) {
        this.colorSectionName = colorSectionName;
        this.hex = hex;
        this.hexString = intToStringHex(this.hex);
        this.baseName = baseName;
        this.isPrimaryColor = isPrimaryColor;
        this.isPrimaryDarkColor = isPrimaryDarkColor;
        this.isAccentColor = isAccentColor;
    }

    public static String intToStringHex(int hex) {
        return String.format("#%06x", 0xFFFFFF & hex);
    }

    public int getHex() {
        return hex;
    }

    public boolean isPrimaryDarkColor() {
        return isPrimaryDarkColor;
    }

    public boolean isPrimaryColor() {
        return isPrimaryColor;
    }

    public void setPrimaryDarkColor(boolean isPrimaryDarkColor) {
        this.isPrimaryDarkColor = isPrimaryDarkColor;
    }

    public boolean isAccentColor() {
        return isAccentColor;
    }

    public void setAccentColor(boolean isAccentColor) {
        this.isAccentColor = isAccentColor;
    }

    public void setPrimaryColor(boolean isPrimaryColor) {
        this.isPrimaryColor = isPrimaryColor;
    }

    public String getHexString() {
        return hexString;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getColorSectionName() {
        return colorSectionName;
    }

    @Override
     public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(colorSectionName);
        out.writeInt(hex);
        out.writeString(baseName);
        byte bPc = (byte) (isPrimaryColor ? 1 : 0);
        byte bPdc = (byte) (isPrimaryDarkColor ? 1 : 0);
        byte bAc = (byte) (isAccentColor ? 1 : 0);
        out.writeByte(bPc);
        out.writeByte(bPdc);
        out.writeByte(bAc);
    }

    public static final Parcelable.Creator<PaletteColor> CREATOR
            = new Parcelable.Creator<PaletteColor>() {
        public PaletteColor createFromParcel(Parcel in) {
            final String colorSectionName = in.readString();
            final int hex = in.readInt();
            final String baseName = in.readString();
            final boolean isPrimaryColor = in.readByte() == 1;
            final boolean isPrimaryDarkColor = in.readByte() == 1;
            final boolean isAccentColor = in.readByte() == 1;
            return new PaletteColor(colorSectionName, hex, baseName, isPrimaryColor, isPrimaryDarkColor, isAccentColor);
        }

        public PaletteColor[] newArray(int size) {
            return new PaletteColor[size];
        }
    };
}
