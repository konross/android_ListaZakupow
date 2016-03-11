package konross.listazakupow;

import android.provider.BaseColumns;

/**
 * Created by Konrad on 2016-03-08.
 */
public class TableData {

    public TableData(){

    }

    public static abstract class Tables implements BaseColumns{

        public static final String DATABASE_NAME="list_table";

        public static final String LISTS_TABLE="lists_table";
        public static final String LIST_NAME="list_name";
        public static final String LIST_ID="lisk1_id";

        public static final String PRODUCTS_TABLE_NAME="products_table";

        public static final String PRODUCT_NAME="product_name";
        public static final String PRODUCT_VALUE="product_value";
        public static final String PRODUCT_ID="prok1_id";
        public static final String PRODUCT_LIST_ID="lis_id";
    }
}
