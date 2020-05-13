public class Test {
    public static void main(String[] args) {
        String asd = "РРРРоман!*?;*фыцвоDSAKFASJKFALF453534";
        String qwe = asd.replaceAll("[а-яА-ЯёЁa-zA-Z0-9]", "");
        System.out.println(qwe);
    }
}
