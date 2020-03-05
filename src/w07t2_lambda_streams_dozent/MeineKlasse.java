package w07t2_lambda_streams_dozent;

public class MeineKlasse
{

    private int i;

    static
    {
        System.out.println("(4)");
    }
    
    {
        System.out.println("(1)");
        i = 13;
        methode();
    }

    public MeineKlasse()
    {
        System.out.println("Im  Konstruktor: " + i);
    }
    
    {
        System.out.println("(2)");
    }

    static
    {
        System.out.println("(3)");
        methodeStatic();
    }

    private static void methodeStatic()
    {
        System.out.println("(5)");
    }
    
   private void methode()
    {
        System.out.println("(6)");
    }
    
}
