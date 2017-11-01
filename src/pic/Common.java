package pic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A couple convenience methods for the whole project.
 */
class Common {

    /**
     * Transform possibly null list into non-null list.
     * @param lst list that may be null
     * @param <Type> type of objects in list
     * @return list guaranteed to be non-null
     */
    static <Type> List<Type> nonNullList(final List<Type> lst){
        if(lst == null){
            return new ArrayList<>();
        }
        return lst;
    }

    /**
     * Transform possibly null set into non-null set
     * @param set set that may be null
     * @param <Type> type of objects in set
     * @return set guaranteed to be non-null
     */
    static <Type> Set<Type> nonNullSet(final Set<Type> set){
        if(set == null){
            return new HashSet<>();
        }
        return set;
    }
}
