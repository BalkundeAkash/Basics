public class ArithmeticMain {

    public static void main(String[] args) {

        int result = AirthmeticOperations.addition(20,4);
        System.out.println(result);

        System.out.println(AirthmeticOperations.PI_VALUE);
        AirthmeticOperations airthmeticOperations = new AirthmeticOperations();
        int resultFromInstanceMethod = airthmeticOperations.sum(20,5);
        System.out.println(resultFromInstanceMethod);

        System.out.println(AirthmeticOperations.PI_VALUE);
    }

}
