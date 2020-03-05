package w07t2_lambda_streams_dozent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class W07T2_Lambda_Streams_Dozent
{
    // ==============================================================

    @FunctionalInterface
    interface Rechner
    {

        public double rechnen(double a, double b);
    }

    // ==============================================================
    @FunctionalInterface
    interface Function<T1, T2>
    {

        T2 rechnen(T1 t);
    }

    // ==============================================================
    private static void lambdaV1() throws Exception
    {
        Callable<Integer> zuf1 = () -> (int) (Math.random() * 100) + 1;
        System.out.println("zuf1.call() = " + zuf1.call());

        Callable<Integer> zuf2 = () ->
        {
            return (int) (Math.random() * 100) + 1;
        };

        System.out.println("zuf2.call() = " + zuf2.call());
    }

    // --------------------------------------------------------------
    private static void rechneMal(Rechner r, double w1, double w2)
    {
        System.out.println("rechneMal: " + r.rechnen(w1, w2));
    }

    // --------------------------------------------------------------
    private static void lambdaV2() throws Exception
    {
        Rechner prd = (w1, w2) ->
        {
            double back = w1 * w2;
            return back;
        };

        Rechner add = (w1, w2) ->
        {
            double back = w1 + w2;
            return back;
        };

        System.out.println("prd.rechnen(7, 13) = " + prd.rechnen(7, 13));
        System.out.println("add.rechnen(7, 13) = " + add.rechnen(7, 13));

        rechneMal(prd, 7, 13);
        rechneMal(add, 7, 13);

        Function<Integer, Double> quadrat = x -> (double) (x * x);
        System.out.println(quadrat.rechnen(5));
    }

    // --------------------------------------------------------------
    private static void praedikate()
    {
        Predicate<String> endetAufTag = (str) ->
        {
            return str.endsWith("tag");
        };

        System.out.println("endetAufTag(\"Dienstag\") = " + endetAufTag.test("Dienstag"));
        System.out.println("endetAufTag(\"Mittwoch\") = " + endetAufTag.test("Mittwoch"));
    }

    // --------------------------------------------------------------
    private static void lambdaV3()
    {
        List<Integer> primzahlen = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31);

        //frueher
       long summe = 0;
        for (int i = 0; i < primzahlen.size(); i++)
        {
            if (primzahlen.get(i) > 13)
            {
                summe += primzahlen.get(i);
            }
        }
        System.out.println("(1) summe = " + summe);

        // als Lambda
        summe = primzahlen.stream()
                .filter(zahl -> zahl > 13)
                .mapToInt(zahl -> zahl)
                .sum();

        System.out.println("(2) summe = " + summe);

        System.out.println("(3) " + primzahlen.stream());

        System.out.println("(4) " + primzahlen.stream()
                .filter(zahl -> zahl > 13));

        System.out.println("(5) " + primzahlen.stream()
                .filter(zahl -> zahl > 13)
                .mapToInt(zahl -> zahl));

        Predicate<Integer> groesserAls13 = (zahl) ->
        {
            return zahl > 13;
        };

        System.out.println("(5) " + primzahlen.stream()
                .filter(zahl -> zahl > 13)
                .mapToInt(zahl -> zahl));

        System.out.println("(5) " + primzahlen.stream()
                .filter(groesserAls13)
                .mapToInt(zahl -> zahl)
                .sum());

        Object[] intArr = primzahlen.stream()
                .filter(groesserAls13)
                .toArray();

        for (Object o : intArr)
        {
            System.out.println("(6) " + o);
        }

        System.out.println("(7) " + primzahlen.stream()
                .filter(groesserAls13)
                .mapToDouble(zahl -> zahl)
                .sum());
    }

    // --------------------------------------------------------------
    private static void lambdaV4()
    {
        List<Integer> liste = Arrays.asList(
                2, 3, 4, 5, 6, 7, 8, 9, 10
        );

        System.out.println("collect: " + 
                liste.stream()
                        .filter(z -> (z % 2 != 0))
                        .collect(Collectors.toList()));

        System.out.println("average: " +
                liste.stream()
                        .filter(z -> (z % 2 != 0))
                        .mapToInt(z -> z) 
                        .average()
                        .getAsDouble());

    }

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    private static void lambdaV5()
    {
        List<String> liste = Arrays.asList("Montag", "Dienstag", "Mittwoch",
                "Donnerstag", "Freitag", "Samstag", "Sonntag");

        System.out.println("(1) "
                + liste.stream()
                        .filter(tag -> (tag.endsWith("tag")))
                        .collect(Collectors.toList()));

        System.out.println("(2) "
                + liste.stream()
                        .filter(tag -> (tag.startsWith("M")))
                        .collect(Collectors.toList()));

        String[] strArr =
        {
            "Montag", "Dienstag", "Mittwoch", "Mittwoch",
            "Donnerstag", "Freitag", "Samstag", "Sonntag"
        };

        System.out.println("(3) "
                + Arrays.stream(strArr)
                        .filter(tag -> (tag.startsWith("M")))
                        .sorted()
                        .collect(Collectors.toList()));

        System.out.println("(4) "
                + Arrays.stream(strArr)
                        .filter(tag -> (tag.startsWith("M")))
                        .sorted() // erfordert Comparable
                        .distinct() // doppelte raus
                        .collect(Collectors.toList()));

        System.out.println("(5) "
                + Arrays.stream(strArr)
                        .distinct()
                        .collect(Collectors.toList()));

        int[] array =
        {
            23, 43, 56, 97, 32
        };
        Arrays.stream(array).reduce((x, y) -> x + y).ifPresent(s -> System.out.println(s));
        Arrays.stream(array).reduce(Integer::sum).ifPresent(s -> System.out.println(s));

        String str = Arrays.stream(strArr)
                .distinct()
                .reduce((m1, m2) -> m1 + ", " + m2).get();//.ifPresent(m -> System.out.println(m));

        System.out.println("(6) " + str);
    }

    // --------------------------------------------------------------
    public static void main(String[] args) throws Exception
    {
        // lambdaV1();
        // lambdaV2();
        // praedikate();
        // lambdaV3();
        // lambdaV4();
        lambdaV5();
        
        /*
        Consumer<String> printQuoted = s -> System.out.printf( "'%s'", s );
        printQuoted.accept( "Chris" );  // 'Chris'
        
        Consumer<String> printNow =  String -> System.out.println( System.currentTimeMillis() );
        printNow.accept( "Chris" );  // 'Chris'
        
        Supplier<Date> factory = Date::new;
        System.out.println( factory.get() ); 
        
        Long[] timestamps = { 2432558632L, 1455872986345L };

        Date thisYear = new GregorianCalendar( 2012, Calendar.JANUARY, 1 ).getTime();

        Arrays.stream( timestamps )
                .map( Date::new )
                .filter( thisYear::before )
                .forEach( System.out::println );  // Fri Feb 19 10:09:46 CET 2016
        
        Predicate<Character> isDigit = Character::isDigit;

        List<Character> list = new ArrayList<>( Arrays.asList( 'a', '1' ) );

        list.removeIf( isDigit );
        System.out.println(list);
        
        List<String> lines = Arrays.asList("spring", "node", "mkyong");

        List<String> result = lines.stream()                // convert list to stream
                .filter(line -> !"mkyong".equals(line))     // we dont like mkyong
                .collect(Collectors.toList());              // collect the output and convert streams to a List

        System.out.println (result);
        result.forEach(System.out::println);   
        
                lines.stream()                // convert list to stream
                .filter(line -> !"mkyong".equals(line))     // we dont like mkyong
                .collect(Collectors.toList())
                .forEach(System.out::println);

        */

    }

}
