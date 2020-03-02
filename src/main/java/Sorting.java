import java.util.ArrayList;
import java.util.List;


public class Sorting {

    public List<DatabaseEntry> categorySort(List<DatabaseEntry> dbListRaw, String category)
    {
        List<DatabaseEntry> dbListSorted = new ArrayList<>();

        for(DatabaseEntry jobfilter : dbListRaw)
        {
            if(jobfilter.getCategory() != null) {
                if (jobfilter.getCategory().toLowerCase().contains(category.toLowerCase())) {
                    dbListSorted.add(jobfilter);
                }
            }
        }

        // iterate over dblistraw and assign it to dbListSorted
        return dbListSorted;
    }
}
