package ch3.item10;

import java.util.Objects;

public class CaseInsensitiveString {
    private final String str;

    public CaseInsensitiveString(String str) {
        this.str = Objects.requireNonNull(str);
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof CaseInsensitiveString )
            return str.equalsIgnoreCase( ((CaseInsensitiveString) obj).str );
        if( obj instanceof String ) // 한방향으로만 동작
            return str.equalsIgnoreCase( (String) obj );

        return false;
    }
}
