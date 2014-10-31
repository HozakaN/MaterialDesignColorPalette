package fr.hozakan.materialdesigncolorpalette.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PaletteColor implements Parcelable {

    private final int hex;
    private final String hexString;
    private final String baseName;
    private final String colorSectionName;
    private boolean isPreviewColor;
    private boolean isActionBarPreviewColor;

    public PaletteColor(String colorSectionName, final int hex, final String baseName, boolean isActionBarPreviewColor, boolean isPreviewColor) {
        this.colorSectionName = colorSectionName;
        this.hex = hex;
        this.hexString = intToStringHex(this.hex);
        this.baseName = baseName;
        this.isActionBarPreviewColor = isActionBarPreviewColor;
        this.isPreviewColor = isPreviewColor;
    }

    public static String intToStringHex(int hex) {
        return String.format("#%06x", 0xFFFFFF & hex);
    }

    public int getHex() {
        return hex;
    }

    public boolean isPreviewColor() {
        return isPreviewColor;
    }

    public boolean isActionBarPreviewColor() {
        return isActionBarPreviewColor;
    }

    public void setPreviewColor(boolean isPreviewColor) {
        this.isPreviewColor = isPreviewColor;
    }

    public void setActionBarPreviewColor(boolean isActionBarPreviewColor) {
        this.isActionBarPreviewColor = isActionBarPreviewColor;
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
        byte bAbpc = (byte) (isActionBarPreviewColor ? 1 : 0);
        byte bPc = (byte) (isPreviewColor ? 1 : 0);
        out.writeByte(bAbpc);
        out.writeByte(bPc);
    }

    public static final Parcelable.Creator<PaletteColor> CREATOR
            = new Parcelable.Creator<PaletteColor>() {
        public PaletteColor createFromParcel(Parcel in) {
            final String colorSectionName = in.readString();
            final int hex = in.readInt();
            final String baseName = in.readString();
            final boolean isActionbarPreviewColor = in.readByte() == 1 ? true : false;
            final boolean isPreviewColor = in.readByte() == 1 ? true : false;
            return new PaletteColor(colorSectionName, hex, baseName, isActionbarPreviewColor, isPreviewColor);
        }

        public PaletteColor[] newArray(int size) {
            return new PaletteColor[size];
        }
    };
}
