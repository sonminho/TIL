package ch5.item26;

import java.util.List;

public class RawType {

    static void unsafeAdd(List list, Object o) {
        list.add(o);
    }

}
