package fr.hozakan.materialdesigncolorpalette.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PaletteColorSection implements Parcelable {
    private final String colorSectionName;
    private final int colorSectionValue;
    private final int darkColorSectionsValue;
    private final List<PaletteColor> paletteColorList;

    public PaletteColorSection(final String colorSectionName, final int colorSectionValue,
                               int darkColorSectionsValue, final List<PaletteColor> paletteColorList) {
        this.colorSectionName = colorSectionName;
        this.colorSectionValue = colorSectionValue;
        this.darkColorSectionsValue = darkColorSectionsValue;
        this.paletteColorList = paletteColorList;
    }

    public String getColorSectionName() {
        return colorSectionName;
    }

    public int getColorSectionValue() {
        return colorSectionValue;
    }

    public int getDarkColorSectionsValue() {
        return darkColorSectionsValue;
    }

    public List<PaletteColor> getPaletteColorList() {
        return paletteColorList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(colorSectionName);
        out.writeInt(colorSectionValue);
        out.writeInt(darkColorSectionsValue);
        out.writeList(paletteColorList);
    }

    public static final Parcelable.Creator<PaletteColorSection> CREATOR
            = new Parcelable.Creator<PaletteColorSection>() {
        public PaletteColorSection createFromParcel(Parcel in) {
            List<PaletteColor> paletteColorList = new ArrayList<PaletteColor>();
            final String colorSectionName = in.readString();
            final int colorSectionValue = in.readInt();
            final int darkColorSectionValue = in.readInt();
            in.readList(paletteColorList, PaletteColorSection.class.getClassLoader());
            return new PaletteColorSection(colorSectionName, colorSectionValue, darkColorSectionValue, paletteColorList);
        }

        public PaletteColorSection[] newArray(int size) {
            return new PaletteColorSection[size];
        }
    };
}
