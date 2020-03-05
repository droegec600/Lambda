package w07t3_uebung_lambda_dozent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class W07T3_Uebung_Lambda_Dozent
{

    // ==============================================================
    /**
     * In einem Functional-Interface darf es nur eine abstrakte Methode geben.
     * Genau diese abstrakte Methode wird durch den Lambda-Ausdruck realisiert.
     * <code>final Mapper<String, Integer> intMapper = s -> s.length();</code>
     * wird einen String zur "map"-Methode liefern und seine Laenge
     * zurueckbekommen.<br>
     * Die - auf diese Art realisierte - "map"-Methode wird aus der
     * default-Methode "mapAll" aufgerufen.
     */
    @FunctionalInterface
    public interface Mapper<S, T>
    {

        //abstract method
        T map(S sType);

        //default method
        default List<T> mapAll(List<S> list)
        {
            List<T> tList = new ArrayList<>();

            for (S s : list)
            {
                // T t;
                // t = map(s);
                tList.add(map(s));
            }

            return tList;
        }
    }

    // ==============================================================
    @FunctionalInterface
    public interface RunnableThatThrows extends Runnable
    {

        @Override
        default void run()
        {
            try
            {
                runThrows();
            } catch (Exception ex)
            {
                throw new RuntimeException(ex.getMessage());
            }
        }

        public void runThrows() throws Exception;
    }

    // ==============================================================
    @FunctionalInterface
    public interface ComparatorThatThrows<T> extends Comparator<T>
    {

        public int compareThrows(final T t1, final T t2) throws Exception;

        @Override
        default public int compare(T t1, T t2)
        {
            int resultat = 0;

            try
            {
                resultat = compareThrows(t1, t2);
            } catch (Exception ex)
            {
                throw new RuntimeException(ex.getMessage());
            }

            return resultat;
        }
    }

    // ==============================================================
    private static void aufgabe3a()
    {
        // liefere Laengen der Listenelemente
        final List<String> names = Arrays.asList("Tim", "Andi", "Michael");
        final Mapper<String, Integer> intMapper = s -> s.length();//String::length;

        System.out.println(intMapper.mapAll(names));

        // schreibe alle Listenelemente gross
        final Mapper<String, String> stringMapper = str -> ">> "
                + str.toUpperCase() + " << ";
        final List<String> uppercaseNames = stringMapper.mapAll(names);
        System.out.println(uppercaseNames);
    }

    // --------------------------------------------------------------
    private static void aufgabe4a()
    {
        final Runnable runner = () ->
        {
            System.out.println("Throwing");
            try
            {
                throw new IOException();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
        };
    }

    // --------------------------------------------------------------
    private static void aufgabe4b()
    {
        final RunnableThatThrows runner2 = () ->
        {
            System.out.println("RunnableThatThrows");
            throw new IOException();
        };

        runner2.run();
    }

    // --------------------------------------------------------------    
    static UnaryOperator<Integer> myFactorialInt;
    static Function<Integer, Long> myFactorialLong;

    private static void aufgabe4d()
    {
        // System.out.println(myFactorialInt);

        myFactorialInt
                = i -> i == 0 ? 1 : i * myFactorialInt.apply(i - 1);

        myFactorialLong
                = i -> i == 0 ? 1 : i * myFactorialLong.apply(i - 1);

        System.out.println(myFactorialInt.apply(5));
        System.out.println(myFactorialLong.apply(5));
        System.out.println(myFactorialInt.apply(20));
        System.out.println(myFactorialLong.apply(20));
        System.out.println(myFactorialInt.apply(50));
        System.out.println(myFactorialLong.apply(50));
    }

    // --------------------------------------------------------------
    private static void aufgabe1a()
    {
        Predicate<Integer> isEven = (i) -> (i % 2 == 0);
        Predicate<Integer> isPositive = (i) -> (i > 0);

        IntPredicate isEven2 = (i) -> (i % 2 == 0);
        IntPredicate isPositive2 = (i) -> (i > 0);

        System.out.println(isEven.test(13));
        System.out.println(isEven.test(12));

        System.out.println(isPositive.test(13));
        System.out.println(isPositive.test(-13));

        System.out.println(isEven2.test(13));
        System.out.println(isEven2.test(12));

        System.out.println(isPositive2.test(13));
        System.out.println(isPositive2.test(-13));
    }

    // --------------------------------------------------------------
    private static void aufgabe1b()
    {
        Predicate<String> isShortWord = (s) -> (s.length() < 4);

        Predicate<Integer> isEven = (i) -> (i % 2 == 0);
        Predicate<Integer> isPositive = (i) -> (i > 0);

        System.out.println(isEven.and(isPositive).test(-12));
        System.out.println(isPositive.and(isEven.negate()).test(13));
    }

    // --------------------------------------------------------------
    private static void aufgabe1c()
    {
        List<String> names = new ArrayList<>();
        names.add("Michael");
        names.add("Tim");
        names.add("Flo");
        names.add("Clemens");

        Predicate<String> isShortWord = (s) -> (s.length() < 4);
        /*
         * names = names.stream() .filter(tag -> (isShortWord.test(tag)))
         * .collect(Collectors.toList());
         */
        names.removeIf(isShortWord);

        System.out.println(names);
    }

    // --------------------------------------------------------------
    private static void aufgabe5()
    {
        int result = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).
                filter(zahl -> zahl % 2 == 1).
                map(zahl -> zahl * zahl).
                sum();
        
        int result2 = IntStream.of(1,2,3,4,5,6,7,8,9,10)
                .filter(zahl -> zahl % 2 == 1)
                . map(zahl -> zahl * zahl)
                . reduce(0, Integer::sum);
        
        System.out.println("result  = " + result);
        System.out.println("result2 = " + result2);
    }

    // --------------------------------------------------------------
    public static void main(String[] args)
    {
        //aufgabe3a();
        //aufgabe4b();
        //aufgabe4d();
        //aufgabe1a();
        //aufgabe1b();
        //aufgabe1c();
        aufgabe5();
    }

    // --------------------------------------------------------------
}
