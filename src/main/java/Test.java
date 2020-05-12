import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String string = "a sdawdw<img src=\"http://localhost:8080/src/main/resources/uploads/mm/tq/ti/avatar.087cb69a.jpg\">awdawwf asdawdw<img src=\\\"http://localhost:8080/src/main/resources/uploads/mm/tq/ti/awewkjrwektj.PNG\\\">awdawwf\"";
        Pattern path = Pattern.compile("src/([\\s\\S]*?).jpg");
        Matcher matcher = path.matcher(string);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        String b = "src/main/resources/uploads/mm/tq/ti/avatar.087cb69a.jpg";

        boolean a = string.contains(b);
        System.out.println(a);


    }
}
