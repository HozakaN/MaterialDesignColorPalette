package fr.hozakan.materialdesigncolorpalette.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PaletteColor implements Parcelable {

    private final int hex;
    private final String hexString;
    private final String baseName;

    public PaletteColor(final int hex, final String baseName) {
        this.hex = hex;
        this.hexString = String.format("#%06X", 0xFFFFFF & this.hex);
        this.baseName = baseName;
    }

    public int getHex() {
        return hex;
    }

    public String getHexString() {
        return hexString;
    }

    public String getBaseName() {
        return baseName;
    }

    @Override
     public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(hex);
        out.writeString(baseName);
    }

    public static final Parcelable.Creator<PaletteColor> CREATOR
            = new Parcelable.Creator<PaletteColor>() {
        public PaletteColor createFromParcel(Parcel in) {
            final int hex = in.readInt();
            final String baseName = in.readString();
            return new PaletteColor(hex, baseName);
        }

        public PaletteColor[] newArray(int size) {
            return new PaletteColor[size];
        }
    };
}
