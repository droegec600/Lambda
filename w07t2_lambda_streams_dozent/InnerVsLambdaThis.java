/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w07t2_lambda_streams_dozent;

class InnerVsLambdaThis {

  InnerVsLambdaThis() {

    Runnable lambdaRun = () -> System.out.println( this.getClass().getName() );

    Runnable innerRun  = new Runnable() {

      @Override public void run() { System.out.println( this.getClass().getName()); }

    };

  

    lambdaRun.run();      // InnerVsLambdaThis

    innerRun.run();       // InnerVsLambdaThis$1

  }

  public static void main( String[] args ) {

    new InnerVsLambdaThis();

  }

}
    

