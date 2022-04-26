package ch3.item10;

public class CaseInsensitiveString2 {
    private final String str;

    public CaseInsensitiveString2(String str) {
        this.str = str;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CaseInsensitiveString2 && ((CaseInsensitiveString2) obj).str.equalsIgnoreCase(str);
    }

}
