import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.vaadin.data.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args)
    {
        String pattern = "^[А-Яа-я]{2,6}$";
        String value = "ыпывп";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(value);
        System.out.println(m.matches());

    }
}
